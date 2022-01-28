package com.brainlux.polyapp.domain.model

import androidx.annotation.Keep

@Keep
data class ActualService(
    val category: String = "",
    var serviceId: String = "",
)
