package com.spbstu.application.domain.model

enum class ServiceCategory {
    UNIVERSITY,
    STUDENT,
    OTHER
}

fun ServiceCategory.getCollection(): String = toString().lowercase()