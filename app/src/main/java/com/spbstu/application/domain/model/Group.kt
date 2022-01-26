package com.spbstu.application.domain.model

import com.spbstu.application.base.BaseModel

data class Group(
    override var id: Long = 0,
    val title: String = "",
    val number: String = ""
) : BaseModel(id) {

    override fun isContentEqual(other: BaseModel): Boolean =
        other is Group && title == other.title
                && number == other.number
}