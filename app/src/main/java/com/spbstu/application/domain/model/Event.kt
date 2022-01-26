package com.spbstu.application.domain.model

import android.os.Parcelable
import com.spbstu.application.base.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    override var id: Long = 0,
    val title: String = "",
    val background: String = "",
    var starts: String = "",
    val ends: String = "",
    val link: String = ""
) : BaseModel(id), Parcelable {

    override fun isContentEqual(other: BaseModel): Boolean =
        other is Event && title == other.title
                && background == other.background
                && starts == other.starts
                && ends == other.ends
                && link == other.link
}