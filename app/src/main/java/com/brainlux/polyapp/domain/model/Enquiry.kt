package com.brainlux.polyapp.domain.model

import androidx.annotation.Keep
import com.brainlux.polyapp.base.BaseModel

@Keep
data class Enquiry(
    override var id: Long = 0,
    val title: String = "",
    val description: String = ""
) : BaseModel(id) {

    override fun isContentEqual(other: BaseModel): Boolean =
        other is Enquiry && title == other.title
                && description == other.description
}
