package com.raywanderlich.api

import com.raywanderlich.API_VERSION
import com.raywanderlich.api.request.VisitorApiRequest
import com.raywanderlich.apiUser
import com.raywanderlich.model.Request
import com.raywanderlich.model.Visitor
import com.raywanderlich.repository.Repository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.post

const val VISITOR_ENDPOINT = "$API_VERSION/visitor"

@Location(VISITOR_ENDPOINT)
class VisitorApi

fun Route.visitorApi(db: Repository) {

    authenticate("jwt") {
        get<VisitorApi>{
            call.respond(db.visitors())
        }

        post<VisitorApi>{

            val user = call.apiUser!!

            try {
                val request = call.receive<VisitorApiRequest>()
                val visitor = db.add(user.userId, request.staffName, request.faculty, request.dept, request.visitReason)
                if (visitor != null){
                    call.respond(visitor)
                }else{
                    call.respondText("Invalid data received", status = HttpStatusCode.InternalServerError)
                }
            }catch (e: Throwable){
                call.respondText("Invalid data received", status = HttpStatusCode.BadRequest)
            }
        }
    }

}