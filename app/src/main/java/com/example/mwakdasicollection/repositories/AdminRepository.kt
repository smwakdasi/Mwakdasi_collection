package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Admin
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class AdminRepository {
    private val db = FirebaseFirestore.getInstance()
    private val adminRef = db.collection("admins")

    fun addAdmin(admin: Admin): Task<Void> {
        return adminRef.document(admin.adminId).set(admin)
    }

    fun getAdmins(): Query {
        return adminRef.limit(10)
    }
}