package com.raywanderlich.model

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Column
import java.io.Serializable

data class Visitor(
    var id: Int,
    val userId: String,
    var staffName: String,
    var faculty: String,
    var dept:String,
    var visitReason : String) : Serializable

object Visitors: IntIdTable(){
    val user: Column<String> = varchar("user_id", 20).index()
    var staffName: Column<String> = varchar("staffName", 255)
    var faculty: Column<String> = varchar("faculty", 255)
    var dept: Column<String> = varchar("dept", 255)
    var visitReason : Column<String> = varchar("visitReason", 255)
}