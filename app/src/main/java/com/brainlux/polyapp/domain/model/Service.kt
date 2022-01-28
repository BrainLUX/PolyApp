package com.brainlux.polyapp.domain.model

import androidx.annotation.Keep
import com.brainlux.polyapp.base.BaseModel

@Keep
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
