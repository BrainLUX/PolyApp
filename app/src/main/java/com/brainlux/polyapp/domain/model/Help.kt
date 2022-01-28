package com.brainlux.polyapp.domain.model

import com.brainlux.polyapp.base.BaseModel
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.annotation.Keep

@Parcelize
@Keep
data class Help(
    override var id: Long = 0,
    var helpId: String = "",
    val title: String = "",
    val createdAt: Long = 0,
    val createdBy: String = "",
    val description: String = "",
    var tagList: List<Tag> = listOf(),
    val link: String = ""
) : BaseModel(id), Parcelable {

    override fun isContentEqual(other: BaseModel): Boolean =
        other is Help && helpId == other.helpId
                && title == other.title
                && createdAt == other.createdAt
                && createdBy == other.createdBy
                && description == other.description
                && tagList == other.tagList
                && link == other.link

    @Keep
    data class FirebaseEntity(
        val title: String = "",
        val createdAt: Long = 0,
        val createdBy: String = "",
        val description: String = "",
        var tags: List<String> = listOf(),
        val link: String = ""
    )
}
