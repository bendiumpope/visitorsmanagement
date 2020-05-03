package com.raywanderlich.wepapp

import com.raywanderlich.model.EPSession
import com.raywanderlich.redirect
import com.raywanderlich.repository.Repository
import com.raywanderlich.securityCode
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.Location
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.locations.get
import io.ktor.sessions.get
import io.ktor.sessions.sessions

const val HOME = "/"

@Location(HOME)
class Home

fun Route.home(db: Repository){
    get<Home>{
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }
        call.respond(FreeMarkerContent("home.ftl", mapOf("user" to user)))
    }
}