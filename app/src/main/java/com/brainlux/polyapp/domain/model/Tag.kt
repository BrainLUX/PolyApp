package com.brainlux.polyapp.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.brainlux.polyapp.base.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Tag(
    override var id: Long = 0,
    val title: String = "",
    val isChecked: Boolean = false
) : BaseModel(id), Parcelable {

    override fun isContentEqual(other: BaseModel): Boolean =
        other is Tag && title == other.title && isChecked == other.isChecked
}
