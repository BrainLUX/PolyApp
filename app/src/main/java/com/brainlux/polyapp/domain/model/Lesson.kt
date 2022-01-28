package com.brainlux.polyapp.domain.model

import androidx.annotation.Keep
import com.brainlux.polyapp.base.BaseModel

@Keep
data class Lesson(
    override val id: Long = 0,
    val start: String,
    val end: String,
    val name: String,
    val type: String,
    val place: String,
    val teacher: String,
    val link: String
) : BaseModel(id) {
    override fun isContentEqual(other: BaseModel): Boolean =
        other is Lesson && id == other.id && start == other.start
                && end == other.end && name == other.name
                && type == other.type && place == other.place
                && teacher == other.teacher && link == other.link
}
