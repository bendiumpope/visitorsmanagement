package com.raywanderlich.repository

import com.raywanderlich.model.ProfilePicture
import com.raywanderlich.model.User
import com.raywanderlich.model.Visitor

interface Repository {

    suspend fun add(userId: String, staffNameValue: String, facultyValue: String, deptValue: String, visitReasonValue: String):Visitor?
    suspend fun visitor(id:Int): Visitor?
    suspend fun visitor(userId:String): List<Visitor>
    suspend fun visitors(): List<Visitor>
    suspend fun updateVisitor(id:Int, newName: String, newFaculty: String, newDept:String, newVisitReason : String)
    suspend fun remove(id:Int):Boolean
    suspend fun remove(id:String):Boolean
    suspend fun clear()
    suspend fun user(userId: String, hash:String? = null): User?
    suspend fun userByEmail(email:String): User?
    suspend fun users(): List<User>
    suspend fun removeuser(id:String): Boolean
    suspend fun createUser(user: User)
    suspend fun updateuser(userId: String, newfirstName: String, newlastName: String,
                           newemailAddress: String, neworgName: String, neworgAddress: String, newpasswordHash: String)

    suspend fun addprofilepicture(userId: String, imageUrl: String)
    suspend fun profilepicture(userId:String): List<ProfilePicture>
    suspend fun profilepictures(): List<ProfilePicture>

}