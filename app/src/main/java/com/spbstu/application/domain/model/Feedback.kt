package com.spbstu.application.domain.model

import com.google.firebase.Timestamp

data class Feedback(
    val email: String = "",
    val message: String = "",
    val sentIn: Timestamp = Timestamp.now()
)