package com.spbstu.application.domain.model

import com.spbstu.application.base.BaseModel
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class News(
    override var id: Long = 0,
    val title: String = "",
    val imageLink: String = "",
    val url: String = ""
) : BaseModel(id), Parcelable {

    override fun isContentEqual(other: BaseModel): Boolean =
        other is News && title == other.title
                && imageLink == other.imageLink
                && url == other.url
}