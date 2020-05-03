package com.raywanderlich

import com.raywanderlich.api.login
import com.raywanderlich.api.visitorApi
import com.raywanderlich.model.EPSession
import com.raywanderlich.model.User
import com.raywanderlich.repository.DatabaseFactory
import com.raywanderlich.repository.VisitorRepository
import com.raywanderlich.wepapp.*
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.freemarker.FreeMarker
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.locations.Locations
import io.ktor.locations.locations
import io.ktor.request.header
import io.ktor.request.host
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.routing.routing
import io.ktor.server.netty.EngineMain
import io.ktor.sessions.SessionTransportTransformerMessageAuthentication
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import java.net.URI
import java.util.concurrent.TimeUnit

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(DefaultHeaders)

    install(StatusPages){
        exception<Throwable>{e ->
            call.respondText(e.localizedMessage,
                ContentType.Text.Plain, HttpStatusCode.InternalServerError)
        }
    }

    install(ContentNegotiation){
        gson()
    }

    install(FreeMarker){
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

//    install(Authentication){
//        basic(name = "auth"){
//           realm = "ktor server"
//            validate{ credentials ->
//                if(credentials.password == "${credentials.name}123") User(credentials.name) else null
//            }
//        }
//    }

    install(Locations)

    install(Sessions){
        cookie<EPSession>("SESSION"){
            transform(SessionTransportTransformerMessageAuthentication(hashKey))
        }
    }
    val hashFunction = { s: String -> hash(s) }
    DatabaseFactory.init()

    val db = VisitorRepository()

    val jwtServices = JwtService()

    install(Authentication){
        jwt ("jwt") {
            verifier(jwtServices.verifier)
            realm = "emojiphrases app"
            validate {
                val payload = it.payload
                val claim = payload.getClaim("id")
                val claimString = claim.asString()
                val user = db.user(claimString)
                user
            }
        }
    }
    routing{
        static("/static"){
            resources("images")
        }
        home(db)
        about(db)
        visitors(db, hashFunction)
        createvisitors(db, hashFunction)
        updatevisitor(db, hashFunction)
        signup(db, hashFunction)
        signin(db, hashFunction)
        signout()
        users(db, hashFunction)
        updateuser(db, hashFunction)
        profile(db, hashFunction)

        //API
        login(db, jwtServices)
        visitorApi(db)
    }
}

const val API_VERSION = "/api/v1"

suspend fun ApplicationCall.redirect(location: Any){
    respondRedirect(application.locations.href(location))
}

fun ApplicationCall.verifyCode(date: Long, user:User, code:String, hashFunction: (String) -> String) =
    securityCode(date,user,hashFunction) == code &&
            (System.currentTimeMillis()-date).let { it > 0 && it < TimeUnit.MILLISECONDS.convert(2, TimeUnit.HOURS) }

fun ApplicationCall.securityCode(date: Long, user:User, hashFunction: (String) -> String) =
    hashFunction("$date:${user.userId}:${request.host()}:${refererHost()}")

fun ApplicationCall.refererHost() = request.header(HttpHeaders.Referrer)?.let { URI.create(it).host }

val ApplicationCall.apiUser get() = authentication.principal<User>()




