package com.brainlux.polyapp.domain.model

import androidx.annotation.Keep
import com.google.firebase.Timestamp

@Keep
data class Feedback(
    val email: String = "",
    val message: String = "",
    val sentIn: Timestamp = Timestamp.now()
)
