package com.brainlux.polyapp.domain.model

import com.brainlux.polyapp.base.BaseModel
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.annotation.Keep

@Parcelize
@Keep
data class Building(
    override var id: Long = 0,
    var buildingId: String = "",
    val title: String = "",
    val background: String = "",
    val description: String = "",
    val address: String = ""
) : BaseModel(id), Parcelable {

    override fun isContentEqual(other: BaseModel): Boolean =
        other is Building && buildingId == other.buildingId && background == other.background

    @Keep
    data class Action(
        override var id: Long = 0,
        val title: String = "",
        val description: String = ""
    ) : BaseModel(id) {

        override fun isContentEqual(other: BaseModel): Boolean =
            other is Action && description == other.description
    }
}
