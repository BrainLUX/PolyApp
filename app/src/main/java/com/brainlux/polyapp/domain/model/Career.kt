package com.brainlux.polyapp.domain.model

import androidx.annotation.Keep
import com.brainlux.polyapp.base.BaseModel

@Keep
data class Career(
    override var id: Long = 0,
    val title: String = "",
    val description: String = "",
    val link: String = ""
) : BaseModel(id) {

    override fun isContentEqual(other: BaseModel): Boolean =
        other is Career && title == other.title
                && description == other.description
                && link == other.link
}
