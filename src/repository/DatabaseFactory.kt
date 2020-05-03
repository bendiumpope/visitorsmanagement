package com.raywanderlich.repository

import com.raywanderlich.model.ProfilePictures
import com.raywanderlich.model.Users
import com.raywanderlich.model.Visitors
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

        fun init(){
            Database.connect(hikari())

            transaction {
                SchemaUtils.create(Visitors)
                SchemaUtils.create(Users)
                SchemaUtils.create(ProfilePictures)
//                Visitors.insert {
//                    it[staffName] = "benedict"
//                    it[faculty] = "Engineering"
//                    it[dept] = "Computer Engineering"
//                    it[visitReason] = "Convocation and Transcript collection"
//                }
            }
        }
        private fun hikari(): HikariDataSource {
            val config = HikariConfig()
            config.driverClassName = "org.postgresql.Driver"
            config.jdbcUrl = System.getenv("JDBC_DATABASE_URL")
            config.maximumPoolSize = 3
            config.isAutoCommit = false
            config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            config.validate()
            return HikariDataSource(config)

        }

        suspend fun <T> dbQuery(
            block: () -> T): T =
            withContext(Dispatchers.IO){
                transaction {block()}
            }
    }
