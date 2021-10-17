package com.spbstu.application.domain.model

import com.spbstu.application.base.BaseModel

data class Service(
    override var id: Long = 0,
    val title: String = "",
    var serviceId: String = "",
    val description: String = "",
    val background: String = ""
) : BaseModel(id) {

    override fun isContentEqual(other: BaseModel): Boolean =
        other is Service && description == other.description && serviceId == other.serviceId && background == other.background
}