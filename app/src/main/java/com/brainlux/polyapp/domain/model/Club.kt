package com.brainlux.polyapp.domain.model

import com.brainlux.polyapp.base.BaseModel
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.annotation.Keep

@Parcelize
@Keep
data class Club(
    override var id: Long = 0,
    val title: String = "",
    val type: String = "",
    val description: String = "",
    var tagList: List<Tag> = listOf(),
    val links: List<Link> = listOf(),
) : BaseModel(id), Parcelable {

    override fun isContentEqual(other: BaseModel): Boolean =
        other is Club && title == other.title
                && type == other.type
                && description == other.description
                && tagList == other.tagList
                && links == other.links

    @Parcelize
    @Keep
    data class Link(
        override var id: Long = 0,
        val title: String = "",
        val link: String = ""
    ) : BaseModel(id), Parcelable {

        override fun isContentEqual(other: BaseModel): Boolean =
            other is Link && title == other.title && link == other.link
    }
}
