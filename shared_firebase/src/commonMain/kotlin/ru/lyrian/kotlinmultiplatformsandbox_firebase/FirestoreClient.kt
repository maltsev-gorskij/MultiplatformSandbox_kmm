package ru.lyrian.kotlinmultiplatformsandbox_firebase

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore

class FirestoreClient {
    private val firestore: FirebaseFirestore = Firebase.firestore

    @Throws(Exception::class)
    suspend fun addUser(
        name: String
    ) {

        val user = hashMapOf(
            "first" to name
        )

        firestore.collection("users").add(user)
    }

    @Throws(Exception::class)
    suspend fun getFirstUser() {
        val result = firestore.collection("users").get().documents[0].get<String>(field = "first")
        println(result)
    }
}