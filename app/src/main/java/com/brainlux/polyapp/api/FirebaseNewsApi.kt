package com.brainlux.polyapp.api

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.brainlux.polyapp.domain.model.NewsTab

object FirebaseNewsApi {
    private const val NEWS_COLLECTION = "news"

    fun getNewsTabs(onItemsLoaded: (List<NewsTab>) -> Unit) {
        Firebase.firestore.collection(NEWS_COLLECTION)
            .get()
            .addOnSuccessListener { snapshot ->
                onItemsLoaded(snapshot.toObjects(NewsTab::class.java))
            }
    }
}