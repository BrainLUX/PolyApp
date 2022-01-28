package com.brainlux.polyapp.domain.model

import androidx.annotation.Keep

@Keep
data class NewsTab(
    val id: Int = 0,
    val title: String = "",
    val url: String = ""
)
