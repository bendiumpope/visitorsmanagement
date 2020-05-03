package com.raywanderlich.repository

import com.raywanderlich.model.*
import com.raywanderlich.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.transaction
import java.lang.IllegalArgumentException

class VisitorRepository : Repository {
    override suspend fun add(
        userId: String,
        staffNameValue: String,
        facultyValue: String,
        deptValue: String,
        visitReasonValue: String
    ) =
        dbQuery {
            val insertStatement = Visitors.insert {
                it[user] = userId
                it[staffName] = staffNameValue
                it[faculty] = facultyValue
                it[dept] = deptValue
                it[visitReason] = visitReasonValue
            }
            val result = insertStatement.resultedValues?.get(0)
            if (result != null){
                toVisitor(result)
            } else{
                null
            }
        }


    override suspend fun visitor(id: Int): Visitor? = dbQuery {
        Visitors.select {
            (Visitors.id eq id)
        }.mapNotNull { toVisitor(it) }
            .singleOrNull()
    }

    override suspend fun visitor(userId: String): List<Visitor> = dbQuery {
        Visitors.select{
            (Visitors.user eq userId)
        }.map{ toVisitor(it) }
    }

    override suspend fun visitors(): List<Visitor> = dbQuery {
        Visitors.selectAll().map { toVisitor(it) }
    }



//    override fun updateEmployee(id: Int, name: String, email: String, city: String) = transaction(db) {
//        Employees.update({Employees.id eq id}){
//            it[Employees.name] = name
//            it[Employees.email] = email
//            it[Employees.city] = city
//        }
//        Unit
//    }

    override suspend fun updateVisitor(
        id: Int,
        newName: String,
        newFaculty: String,
        newDept: String,
        newVisitReason: String
    ) = dbQuery {
        Visitors.update({ Visitors.id eq id }) {
            it[staffName] = newName
            it[faculty] = newFaculty
            it[dept] = newDept
            it[visitReason] = newVisitReason

        }
        Unit
    }

    override suspend fun remove(id: Int): Boolean {
        if (visitor(id) == null) {
            throw IllegalArgumentException("No phrase found for id $id.")
        }
        return dbQuery {
            Visitors.deleteWhere { Visitors.id eq id } > 0
        }
    }

    override suspend fun remove(id: String): Boolean {
        return remove(id.toInt())
    }

    override suspend fun clear() {
        Visitors.deleteAll()
    }

    override suspend fun user(userId: String, hash: String?): User? {
        val user = dbQuery {
            Users.select {
                (Users.id eq userId)
            }.mapNotNull { toUser(it) }
                .singleOrNull()
        }
        return when {
            user == null -> null
            hash == null -> user
            user.passwordHash == hash -> user
            else -> null
        }

    }

    override suspend fun users(): List<User> = dbQuery {
        Users.selectAll().map { toUser(it) }
    }

    override suspend fun removeuser(userId: String): Boolean {
        if (visitor(userId) == null) {
            throw IllegalArgumentException("No User found for id $userId.")
        }
        return dbQuery {
            Users.deleteWhere { Users.id eq userId } > 0
        }
    }

    override suspend fun userByEmail(email: String) = dbQuery {
        Users.select { Users.emailAddress.eq(email) }
            .map {
                User(
                    it[Users.id],
                    it[Users.firstName],
                    it[Users.lastName],
                    email,
                    it[Users.orgName],
                    it[Users.orgAddress],
                    it[Users.passwordHash]
                )
            }.singleOrNull()
    }


    override suspend fun createUser(user: User) = dbQuery {
        Users.insert {
            it[id] = user.userId
            it[firstName] = user.firstName
            it[lastName] = user.lastName
            it[emailAddress] = user.emailAddress
            it[orgName] = user.orgName
            it[orgAddress] = user.orgAddress
            it[passwordHash] = user.passwordHash
        }
        Unit
    }

    override suspend fun updateuser(
        userId: String,
        newfirstName: String,
        newlastName: String,
        newemailAddress: String,
        neworgName: String,
        neworgAddress: String,
        newpasswordHash: String
    ) = dbQuery {
        Users.update({ Users.id eq userId }) {
            it[firstName] = newfirstName
            it[lastName] = newlastName
            it[emailAddress] = newemailAddress
            it[orgName] = neworgName
            it[orgAddress] = neworgAddress
            it[passwordHash] = newpasswordHash

        }
        Unit
    }

    override suspend fun addprofilepicture(userId: String, imageUrlValue: String) {
        transaction {
            ProfilePictures.insert {
                it[user] = userId
                it[imageUrl] = imageUrlValue

            }
        }
    }

    override suspend fun profilepicture(userId: String): List<ProfilePicture> = dbQuery{
        ProfilePictures.select{
            (ProfilePictures.user eq userId)
        }.map { toProfilePicture(it) }

    }


    override suspend fun profilepictures(): List<ProfilePicture> = dbQuery {
        ProfilePictures.selectAll().map { toProfilePicture(it) }
    }

    private fun toVisitor(row: ResultRow): Visitor =
        Visitor(
            id = row[Visitors.id].value,
            userId = row[Visitors.user],
            staffName = row[Visitors.staffName],
            faculty = row[Visitors.faculty],
            dept = row[Visitors.dept],
            visitReason = row[Visitors.visitReason]

        )

    private fun toUser(row: ResultRow): User =
        User(
            userId = row[Users.id],
            firstName = row[Users.firstName],
            lastName = row[Users.lastName],
            emailAddress = row[Users.emailAddress],
            orgName = row[Users.orgName],
            orgAddress = row[Users.orgAddress],
            passwordHash = row[Users.passwordHash]
        )

    private fun toProfilePicture(row: ResultRow): ProfilePicture =
        ProfilePicture(
            id = row[ProfilePictures.id].value,
            userId = row[ProfilePictures.user],
            imageUrl = row[ProfilePictures.imageUrl]
        )
}