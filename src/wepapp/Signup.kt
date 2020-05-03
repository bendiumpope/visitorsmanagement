package com.raywanderlich.wepapp

import com.raywanderlich.MIN_PASSWORD_LENGTH
import com.raywanderlich.MIN_USER_ID_LENGTH
import com.raywanderlich.model.EPSession
import com.raywanderlich.model.User
import com.raywanderlich.redirect
import com.raywanderlich.repository.Repository
import com.raywanderlich.userNameValid
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.application.log
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.Parameters
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set

const val SIGNUP = "/signup"

@Location(SIGNUP)
data class Signup(
    val userId: String="",
    val firstName: String="",
    val lastName: String="",
    val emailAddress: String="",
    val orgName: String="",
    val orgAddress: String="",
    val error: String = ""
)

fun Route.signup(db: Repository, hashFunction: (String) -> String){
    post<Signup>{
        val user = call.sessions.get<EPSession>()?.let { user -> db.user(user.userId) }

        if (user != null) return@post call.redirect(Visitors())

        val signUpParameters = call.receive<Parameters>()

        val userId = signUpParameters["userName"] ?: return@post call.redirect(it)
        val firstName = signUpParameters["firstName"] ?: return@post call.redirect(it)
        val lastName = signUpParameters["lastName"] ?: return@post call.redirect(it)
        val emailAddress = signUpParameters["emailAddress"] ?: return@post call.redirect(it)
        val orgName = signUpParameters["orgName"] ?: return@post call.redirect(it)
        val orgAddress = signUpParameters["orgAddress"] ?: return@post call.redirect(it)
        val password = signUpParameters["password"] ?: return@post call.redirect(it)
        val confirmPassword = signUpParameters["confirmPassword"] ?: return@post call.redirect(it)

        val signupError = Signup(userId, lastName, emailAddress)

        when{
            password.length < MIN_PASSWORD_LENGTH ->
                call.redirect(signupError.copy(error = "Password should be at least $MIN_PASSWORD_LENGTH characters long"))
            password != confirmPassword ->
                call.redirect(signupError.copy(error = "Password/confirm password dont match"))
            userId.length < MIN_USER_ID_LENGTH ->
                call.redirect(signupError.copy(error = "Username should be at least $MIN_USER_ID_LENGTH characters long"))
            !userNameValid(userId) ->
                call.redirect(signupError.copy(error = "Username should consist of digits, letters, dots or underscores"))
            db.user(userId) != null ->
                call.redirect(signupError.copy(error = "User with the following username is already registered"))

            else -> {
                val hash = hashFunction(password)
                val newUser = User(userId, firstName, lastName, emailAddress, orgName, orgAddress, hash)

                try {
                    db.createUser(newUser)
                } catch (e: Throwable){
                    when{
                        db.user(userId) != null ->
                            call.redirect(signupError.copy(error = "User with the following username $ is already registered"))
                        db.userByEmail(emailAddress) != null ->
                            call.redirect(signupError.copy(error = "User with the following email $emailAddress is already registered"))
                        else ->{
                            application.log.error("Failed to register user", e)
                            call.redirect(signupError.copy(error = "Failed to register"))
                            println("an error occured $e")
                        }
                    }
                }

                call.sessions.set(EPSession(newUser.userId))
                call.redirect(Createvisitors())
            }
        }
    }

    get<Signup>{
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }

        if (user != null){
            call.redirect(Visitors())
        } else{
            call.respond(FreeMarkerContent("signup.ftl", mapOf("error" to it.error)))
        }
    }
}