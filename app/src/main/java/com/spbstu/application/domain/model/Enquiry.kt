package com.spbstu.application.domain.model

import com.spbstu.application.base.BaseModel

data class Enquiry(
    override var id: Long = 0,
    val title: String = "",
    val description: String = ""
) : BaseModel(id) {

    override fun isContentEqual(other: BaseModel): Boolean =
        other is Enquiry && title == other.title
                && description == other.description
}