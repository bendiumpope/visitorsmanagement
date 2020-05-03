package com.raywanderlich.wepapp

import com.raywanderlich.model.EPSession
import com.raywanderlich.redirect
import com.raywanderlich.repository.Repository
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions

const val UPDATEUSER = "/updateuser"

@Location(UPDATEUSER)
class Updateuser

fun Route.updateuser(db: Repository, hashFunction: (String) -> String) {

    get<Updateuser> {
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }
        //println(user)

        if (user == null) {
            call.redirect(Signin())
        } else {

            call.respond(FreeMarkerContent("updatevisitor.ftl", null))

        }
    }

    post<Updateuser>{
        val params = call.receiveParameters()
        val action = params["action"] ?: throw java.lang.IllegalArgumentException("Missing parameter: action")

        when (action) {
            "update" -> {
                val id = params["id"] ?: throw java.lang.IllegalArgumentException("Missing parameter: id")
                val firstName = params["firstName"] ?: throw IllegalArgumentException("Missing parameter: First Name")
                val lastName = params["lastName"] ?: throw IllegalArgumentException("Missing parameter: Last Name")
                val emailAddress = params["emailAddress"] ?: throw IllegalArgumentException("Missing parameter: Email Address")
                val orgName = params["orgName"] ?: throw IllegalArgumentException("Missing parameter: Name of Organization")
                val orgAddress = params["orgAddress"] ?: throw IllegalArgumentException("Missing parameter: Organization Address")
                val passwordHash = params["passwordHash"] ?: throw IllegalArgumentException("Missing parameter: PasswordHash")
                db.updateuser(id, firstName, lastName, emailAddress, orgName, orgAddress, passwordHash)
                //println("the id ${id}")
                call.redirect(Users())
            }
        }

    }
}
