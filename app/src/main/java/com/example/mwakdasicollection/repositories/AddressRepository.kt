package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Address
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class AddressRepository {
    private val db = FirebaseFirestore.getInstance()
    private val addressRef = db.collection("addresses")

    // Adds an address to Firestore
    fun addAddress(address: Address): Task<Void> {
        return addressRef.document(address.addressId).set(address)
    }

    // Fetches all addresses associated with a specific userId
    fun getUserAddresses(userId: String): Query {
        return addressRef.whereEqualTo("userId", userId)
    }

    // Deletes an address by its ID
    fun deleteAddress(addressId: String): Task<Void> {
        return addressRef.document(addressId).delete()
    }

    // Updates an address by its ID with new field values
    fun updateAddress(addressId: String, updates: Map<String, Any>): Task<Void> {
        return addressRef.document(addressId).update(updates)
    }

    // Retrieves a single address by its ID
    fun getAddressById(addressId: String): Task<com.google.firebase.firestore.DocumentSnapshot> {
        return addressRef.document(addressId).get()
    }
}