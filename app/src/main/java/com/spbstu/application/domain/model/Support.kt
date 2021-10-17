package com.spbstu.application.domain.model

import com.spbstu.application.base.BaseModel

data class Support(
    override var id: Long = 0,
    val title: String = "",
    val description: String = "",
    val documents: String = "",
    val payment: String = ""
) : BaseModel(id) {

    override fun isContentEqual(other: BaseModel): Boolean =
        other is Support && title == other.title
                && description == other.description
                && documents == other.documents
                && payment == other.payment
}