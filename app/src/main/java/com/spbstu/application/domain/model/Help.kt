package com.spbstu.application.domain.model

import com.spbstu.application.base.BaseModel
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
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

    data class FirebaseEntity(
        val title: String = "",
        val createdAt: Long = 0,
        val createdBy: String = "",
        val description: String = "",
        var tags: List<String> = listOf(),
        val link: String = ""
    )
}