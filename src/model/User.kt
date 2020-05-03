package com.raywanderlich.model

import io.ktor.auth.Principal
import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class User(
    val userId: String,
    val firstName: String="",
    val lastName: String="",
    val emailAddress: String="",
    val orgName: String="",
    val orgAddress: String="",
    val passwordHash: String
): Serializable, Principal

object Users: Table(){
    val id = varchar("id", 20).primaryKey()
    var firstName = varchar("first_name", 256)
    var lastName = varchar("last_name", 256)
    var emailAddress = varchar("email_address", 128).uniqueIndex()
    var orgName = varchar("org_name", 256)
    var orgAddress = varchar("org_address", 256)
    var passwordHash = varchar("password_hash", 64)

}

