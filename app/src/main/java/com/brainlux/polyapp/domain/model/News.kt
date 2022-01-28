package com.brainlux.polyapp.domain.model

import com.brainlux.polyapp.base.BaseModel
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.annotation.Keep

@Parcelize
@Keep
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
