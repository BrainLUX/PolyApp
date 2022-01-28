package com.brainlux.polyapp.domain.model

import androidx.annotation.Keep
import com.brainlux.polyapp.base.BaseModel

@Keep
data class Faculty(
    override var id: Long = 0,
    val title: String = "",
    val link: String = ""
) : BaseModel(id) {

    override fun isContentEqual(other: BaseModel): Boolean =
        other is Faculty && title == other.title
                && link == other.link
}
