package com.spbstu.application.domain.model

import com.spbstu.application.base.BaseModel

data class Faculty(
    override var id: Long = 0,
    val title: String = "",
    val link: String = ""
) : BaseModel(id) {

    override fun isContentEqual(other: BaseModel): Boolean =
        other is Faculty && title == other.title
                && link == other.link
}