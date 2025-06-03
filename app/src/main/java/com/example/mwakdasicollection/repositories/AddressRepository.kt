package com.example.mwakdasicollection.repositories

import com.example.mwakdasicollection.model.Address
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class AddressRepository {
    private val db = FirebaseFirestore.getInstance()
    private val addressRef = db.collection("addresses")

    fun addAddress(address: Address): Task<Void> {
        return addressRef.document(address.addressId).set(address)
    }

    fun getUserAddresses(userId: String): Query {
        return addressRef.whereEqualTo("userId", userId)
    }
}
