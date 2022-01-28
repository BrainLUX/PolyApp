package com.brainlux.polyapp.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.brainlux.polyapp.base.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
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
