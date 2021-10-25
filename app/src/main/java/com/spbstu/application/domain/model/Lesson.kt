package com.spbstu.application.domain.model

import com.spbstu.application.base.BaseModel

data class Lesson(
    override val id: Long = 0,
    var start: String,
    var end: String,
    var name: String,
    var type: String,
    var place: String,
    var teacher: String
) : BaseModel(id) {
    override fun isContentEqual(other: BaseModel): Boolean =
        other is Lesson && id == other.id && start == other.start
                && end == other.end && name == other.name
                && type == other.type && place == other.place
                && teacher == other.teacher
}
