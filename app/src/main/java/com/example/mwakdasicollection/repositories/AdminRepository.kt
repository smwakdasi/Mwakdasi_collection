package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Admin
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class AdminRepository {
    private val db = FirebaseFirestore.getInstance()
    private val adminRef = db.collection("admins")

    // Add a new admin to Firestore
    fun addAdmin(admin: Admin): Task<Void> {
        return adminRef.document(admin.adminId).set(admin)
    }

    // Get a list of admins (with an optional limit)
    fun getAdmins(limit: Long = 10): Query {
        return adminRef.limit(limit)
    }

    // Get a specific admin by ID
    fun getAdminById(adminId: String): Task<com.google.firebase.firestore.DocumentSnapshot> {
        return adminRef.document(adminId).get()
    }

    // Update an admin's specific fields
    fun updateAdmin(adminId: String, updates: Map<String, Any>): Task<Void> {
        return adminRef.document(adminId).update(updates)
    }

    // Delete an admin by ID
    fun deleteAdmin(adminId: String): Task<Void> {
        return adminRef.document(adminId).delete()
    }
}