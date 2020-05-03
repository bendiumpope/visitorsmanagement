package com.raywanderlich.wepapp

import com.raywanderlich.model.EPSession
import com.raywanderlich.redirect
import com.raywanderlich.repository.Repository
import com.raywanderlich.securityCode
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
import java.lang.IllegalArgumentException

const val USERS = "/users"

@Location(USERS)
class Users

fun Route.users(db: Repository, hashFunction: (String) -> String) {


    get<Users> {
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }
        // println(user)

        if (user == null) {
            call.redirect(Signin())
        } else {

            val users = db.users()
            val profile = db.profilepicture(user.userId)
            val profileUrls = profile.get(profile.lastIndex).imageUrl
            println(users.toString())
            val date = System.currentTimeMillis()
            val code = call.securityCode(date, user, hashFunction)
            call.respond(
                FreeMarkerContent(
                    "users.ftl",
                    mapOf("users" to users, "user" to user, "profileUrls" to profileUrls, "date" to date, "code" to code), user.userId
                )
            )

        }

    }

    post<Users>{
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }
        val params = call.receiveParameters()
        val action = params["action"] ?: throw IllegalArgumentException("Missing parameter: action")

        when(action){
            "delete" -> {
                val id = params["id"] ?: throw IllegalArgumentException("Missing parameter: id")
                db.removeuser(id)
                call.redirect(Users())
            }
            "edit"->{
                val id = params["id"] ?: throw IllegalArgumentException("Missing parameter: id")
                    println("the id ${id}")
                val updateUser = db.user(id)
                    println("the user is ${updateUser.toString()}")
                if(user == null){
                    call.redirect(Signin())
                } else{
                    val date = System.currentTimeMillis()
                    val code = call.securityCode(date, user, hashFunction)

                    call.respond(FreeMarkerContent("updateuser.ftl",
                        mapOf( "updateUser" to updateUser, "user" to user, "date" to date, "code" to code, "id" to id), user.userId))
                }
            }
        }
    }
}