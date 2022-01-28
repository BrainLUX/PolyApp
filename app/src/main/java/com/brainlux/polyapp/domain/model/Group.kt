package com.brainlux.polyapp.domain.model

import androidx.annotation.Keep
import com.brainlux.polyapp.base.BaseModel

@Keep
data class Group(
    override var id: Long = 0,
    val title: String = "",
    val number: String = ""
) : BaseModel(id) {

    override fun isContentEqual(other: BaseModel): Boolean =
        other is Group && title == other.title
                && number == other.number
}
