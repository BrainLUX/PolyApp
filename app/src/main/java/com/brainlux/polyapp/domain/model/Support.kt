package com.brainlux.polyapp.domain.model

import androidx.annotation.Keep
import com.brainlux.polyapp.base.BaseModel

@Keep
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
