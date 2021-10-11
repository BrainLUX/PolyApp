package com.polytech.application.base

open class BaseModel(open val id: Long = 0, open val value: String = "") {

    open fun isModelEqual(other: BaseModel): Boolean = id == other.id
    open fun isContentEqual(other: BaseModel): Boolean =
        value == other.value
}