package com.raywanderlich.wepapp

import com.raywanderlich.model.EPSession
import com.raywanderlich.model.User
import com.raywanderlich.redirect
import com.raywanderlich.repository.Repository
import com.raywanderlich.securityCode
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
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
import java.lang.IllegalArgumentException

const val VISITORS = "/visitors"

@Location(VISITORS)
class Visitors

fun Route.visitors(db:Repository, hashFunction: (String) -> String){


        get<Visitors>{
            val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }
           // println(user)

            if(user == null){
                call.redirect(Signin())
            } else{

                val visitors = db.visitor(user.userId)
                val profile = db.profilepicture(user.userId)
                val profileUrls = profile.get(profile.lastIndex).imageUrl
                 println(visitors.toString())
                val date = System.currentTimeMillis()
                val code = call.securityCode(date, user, hashFunction)
                call.respond(FreeMarkerContent("visitors.ftl",
                    mapOf("visitors" to visitors, "user" to user, "profileUrls" to profileUrls, "date" to date, "code" to code), user.userId))

            }

        }

        post<Visitors>{
            val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }
            val params = call.receiveParameters()
            val action = params["action"] ?: throw java.lang.IllegalArgumentException("Missing parameter: action")

            when(action){
                "delete" -> {
                    val id = params["id"] ?: throw IllegalArgumentException("Missing parameter: id")
                    db.remove(id)
                    call.redirect(Visitors())
                }
                "edit"->{
                    val id = params["id"] ?: throw IllegalArgumentException("Missing parameter: id")
//                    println("the id ${id}")
                    val visitor = db.visitor(id.toInt())
//                    println("${visitor.toString()}")
                    if(user == null){
                        call.redirect(Signin())
                    } else{
                        val date = System.currentTimeMillis()
                        val code = call.securityCode(date, user, hashFunction)

                    call.respond(FreeMarkerContent("updatevisitor.ftl",
                        mapOf( "visitor" to visitor, "user" to user, "date" to date, "code" to code, "id" to id), user.userId))
                    }
                }
            }
        }

}