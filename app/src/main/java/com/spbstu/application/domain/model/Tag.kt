package com.spbstu.application.domain.model

import android.os.Parcelable
import com.spbstu.application.base.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tag(
    override var id: Long = 0,
    val title: String = "",
    val isChecked: Boolean = false
) : BaseModel(id), Parcelable {

    override fun isContentEqual(other: BaseModel): Boolean =
        other is Tag && title == other.title && isChecked == other.isChecked
}