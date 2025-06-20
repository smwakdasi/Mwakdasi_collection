package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Address
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class AddressRepository {
    private val db = FirebaseFirestore.getInstance()
    private val addressRef = db.collection("addresses")

    /**
     * Add a new address to Firestore.
     *
     * @param address The [Address] object to be added.
     * @return A [Result] containing `true` on success or an [Exception] on failure.
     */
    suspend fun addAddress(address: Address): Result<Boolean> {
        return try {
            addressRef.document(address.addressId).set(address).await()
            Result.success(true) // Successfully added
        } catch (e: Exception) {
            Result.failure(e) // Handle error
        }
    }

    /**
     * Fetch all addresses associated with a specific userId.
     *
     * @param userId The user ID whose addresses are being fetched.
     * @return A [Result] containing a list of [Address] objects or an [Exception] on failure.
     */
    suspend fun getUserAddresses(userId: String): Result<List<Address>> {
        return try {
            val snapshot = addressRef.whereEqualTo("userId", userId).get().await()
            val addresses = snapshot.toObjects(Address::class.java)
            Result.success(addresses) // Successfully fetched
        } catch (e: Exception) {
            Result.failure(e) // Handle error
        }
    }

    /**
     * Delete an address by its ID.
     *
     * @param addressId The ID of the address to delete.
     * @return A [Result] containing `true` on success or an [Exception] on failure.
     */
    suspend fun deleteAddress(addressId: String): Result<Boolean> {
        return try {
            addressRef.document(addressId).delete().await()
            Result.success(true) // Successfully deleted
        } catch (e: Exception) {
            Result.failure(e) // Handle error
        }
    }

    /**
     * Update an address with new field values.
     *
     * @param addressId The ID of the address to update.
     * @param updates A map of fields to update.
     * @return A [Result] containing `true` on success or an [Exception] on failure.
     */
    suspend fun updateAddress(addressId: String, updates: Map<String, Any>): Result<Boolean> {
        return try {
            addressRef.document(addressId).update(updates).await()
            Result.success(true) // Successfully updated
        } catch (e: Exception) {
            Result.failure(e) // Handle error
        }
    }

    /**
     * Retrieve an address by its ID.
     *
     * @param addressId The ID of the address to retrieve.
     * @return A [Result] containing the [Address] or `null` if not found, or an [Exception] on failure.
     */
    suspend fun getAddressById(addressId: String): Result<Address?> {
        return try {
            val document = addressRef.document(addressId).get().await()
            if (document.exists()) {
                Result.success(document.toObject(Address::class.java)) // Successfully found
            } else {
                Result.success(null) // Address not found
            }
        } catch (e: Exception) {
            Result.failure(e) // Handle error
        }
    }
}