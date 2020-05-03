package com.raywanderlich.wepapp

import com.raywanderlich.model.EPSession
import com.raywanderlich.model.Visitor
import com.raywanderlich.redirect
import io.ktor.response.respondRedirect
import com.raywanderlich.repository.Repository
import com.raywanderlich.securityCode
import com.raywanderlich.verifyCode
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.Location
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.sessions.get
import io.ktor.sessions.sessions

const val CREATEVISITORS = "/createvisitors"

@Location(CREATEVISITORS)
class Createvisitors

fun Route.createvisitors(db: Repository, hashFunction: (String) -> String) {

    get<Createvisitors> {
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }

        if(user == null){
            call.redirect(Signin())
        } else{

            val visitors = db.visitors()
            val profile = db.profilepicture(user.userId)
            val profileUrls = profile.get(profile.lastIndex).imageUrl
            val date = System.currentTimeMillis()
            //println("todays date is $date")
            val code = call.securityCode(date, user, hashFunction)
            call.respond(FreeMarkerContent("create.ftl",
                mapOf("visitors" to visitors, "profileUrls" to profileUrls, "user" to user, "date" to date, "code" to code), user.userId))

        }
    }

    post<Createvisitors> {
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }
        val params = call.receiveParameters()
        val date = params["date"] ?.toLongOrNull() ?: return@post call.redirect(it)
        val code = params["code"] ?: return@post call.redirect(it)
        val action = params["action"] ?: throw java.lang.IllegalArgumentException("Missing parameter: action")

        if(user == null || !call.verifyCode(date, user,code,hashFunction)){
            call.redirect(Signin())
        }

        when (action) {
            "add" -> {
                val staffName = params["staffName"] ?: throw IllegalArgumentException("Missing parameter: Staff Name")
                val faculty = params["faculty"] ?: throw IllegalArgumentException("Missing parameter: Faculty")
                val dept = params["dept"] ?: throw IllegalArgumentException("Missing parameter: dept")
                val visitReason = params["visitReason"] ?: throw IllegalArgumentException("Missing parameter: Reason for visit")

                db.add(user!!.userId, staffName, faculty, dept, visitReason)

            }
        }
        call.redirect(Visitors())
    }
}
