package com.brainlux.polyapp.base

abstract class BaseModel(open val id: Long = 0) {

    open fun isModelEqual(other: BaseModel): Boolean = id == other.id
    abstract fun isContentEqual(other: BaseModel): Boolean
}