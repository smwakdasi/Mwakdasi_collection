package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Admin
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class AdminRepository {
    private val db = FirebaseFirestore.getInstance()
    private val adminRef = db.collection("admins")

    /**
     * Add a new admin to Firestore.
     *
     * @param admin The admin object to add.
     * @return A [Result] indicating success or failure.
     */
    suspend fun addAdmin(admin: Admin): Result<Boolean> {
        return try {
            adminRef.document(admin.adminId).set(admin).await()
            Result.success(true) // Successfully added
        } catch (e: Exception) {
            Result.failure(e) // Handle error
        }
    }

    /**
     * Get a list of admins with an optional limit.
     *
     * @param limit The maximum number of records to fetch. Default is 10.
     * @return A [Result] containing a list of `Admin` or an `Exception` on failure.
     */
    suspend fun getAdmins(limit: Long = 10): Result<List<Admin>> {
        return try {
            val snapshot = adminRef.limit(limit).get().await()
            val admins = snapshot.documents.mapNotNull { document ->
                document.toObject(Admin::class.java)
            }
            Result.success(admins)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get a specific admin by their ID.
     *
     * @param adminId The ID of the admin to fetch.
     * @return A [Result] containing the [Admin] or null if not found, or an `Exception`.
     */
    suspend fun getAdminById(adminId: String): Result<Admin?> {
        return try {
            val document = adminRef.document(adminId).get().await()
            if (document.exists()) {
                Result.success(document.toObject(Admin::class.java)) // Return the found admin
            } else {
                Result.success(null) // Admin not found
            }
        } catch (e: Exception) {
            Result.failure(e) // Handle error
        }
    }

    /**
     * Update an admin's specific fields.
     *
     * @param adminId The ID of the admin to update.
     * @param updates A map containing the fields to update.
     * @return A [Result] indicating success or failure.
     */
    suspend fun updateAdmin(adminId: String, updates: Map<String, Any>): Result<Boolean> {
        return try {
            adminRef.document(adminId).update(updates).await()
            Result.success(true) // Successfully updated
        } catch (e: Exception) {
            Result.failure(e) // Handle error
        }
    }

    /**
     * Delete an admin by their ID.
     *
     * @param adminId The ID of the admin to delete.
     * @return A [Result] indicating success or failure.
     */
    suspend fun deleteAdmin(adminId: String): Result<Boolean> {
        return try {
            adminRef.document(adminId).delete().await()
            Result.success(true) // Successfully deleted
        } catch (e: Exception) {
            Result.failure(e) // Handle error
        }
    }
}