package com.brainlux.polyapp.domain.model

import androidx.annotation.Keep
import com.brainlux.polyapp.base.BaseModel

@Keep
data class Eat(
    override var id: Long = 0,
    val title: String = "",
    val address: String = "",
    val time: String = "",
    val mapUrl: String = "",
    val type: Type = Type.CAFE
) : BaseModel(id) {
    override fun isContentEqual(other: BaseModel): Boolean =
        other is Eat && title == other.title
                && title == other.title
                && address == other.address
                && time == other.time
                && mapUrl == other.mapUrl
                && type == other.type

    enum class Type(val index: Long) {
        CANTEEN(1L), CAFE(2L)
    }
}
