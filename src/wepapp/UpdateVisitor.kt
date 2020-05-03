package com.raywanderlich.wepapp

import com.raywanderlich.model.EPSession
import com.raywanderlich.model.Visitor
import com.raywanderlich.redirect
import com.raywanderlich.repository.Repository
import com.raywanderlich.securityCode
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.Location
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import java.util.*


const val UPDATEVISITOR = "/updatevisitor"

@Location(UPDATEVISITOR)
class Updatevisitor

fun Route.updatevisitor(db: Repository, hashFunction: (String) -> String){

    get<Updatevisitor>{
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }
         //println(user)

        if(user == null){
            call.redirect(Signin())
        } else{

            call.respond(FreeMarkerContent("updatevisitor.ftl", null))

        }
    }

    post<Updatevisitor>{
        val params = call.receiveParameters()
        val action = params["action"] ?: throw java.lang.IllegalArgumentException("Missing parameter: action")

        when (action) {
            "update" -> {
                val id = params["id"] ?: throw java.lang.IllegalArgumentException("Missing parameter: id")
                val staffName = params["staffName"] ?: throw IllegalArgumentException("Missing parameter: Staff Name")
                val faculty = params["faculty"] ?: throw IllegalArgumentException("Missing parameter: Faculty")
                val dept = params["dept"] ?: throw IllegalArgumentException("Missing parameter: dept")
                val visitReason = params["visitReason"] ?: throw IllegalArgumentException("Missing parameter: Reason for visit")
                db.updateVisitor(id.toInt(), staffName, faculty, dept, visitReason)
                //var date = Date().toString()
                //println("the id ${id}")
                //println("${date}")
                call.redirect(Visitors())
            }
        }

    }
}
