package com.brainlux.polyapp.api

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.brainlux.polyapp.domain.model.*

object FirebaseFeedbackApi {
    private const val FEEDBACK_COLLECTION = "feedback"

    fun sendFeedback(email: String, message: String) {
        Firebase.firestore.collection(FEEDBACK_COLLECTION).add(
            Feedback(
                email,
                message,
                Timestamp.now()
            )
        )
    }
}