package com.raywanderlich.wepapp

import com.raywanderlich.model.EPSession
import com.raywanderlich.repository.Repository
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.Location
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.locations.get
import io.ktor.sessions.get
import io.ktor.sessions.sessions

const val ABOUT = "/about"

@Location(ABOUT)
class About

fun Route.about(db:Repository){
    get<About>{
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }
        call.respond(FreeMarkerContent("about.ftl", mapOf("user" to user)))
    }
}