package com.raywanderlich.model

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Column
import java.io.Serializable

data class ProfilePicture(
    var id: Int,
    val userId: String,
    var imageUrl: String) : Serializable

object ProfilePictures: IntIdTable(){
    val user: Column<String> = varchar("user_id", 20).index()
    var imageUrl: Column<String> = varchar("imageURL", 255)
}