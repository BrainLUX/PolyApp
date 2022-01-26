package com.spbstu.application.domain.model

import com.google.gson.annotations.SerializedName

data class TimetableDTO(
    @SerializedName("week")
    val weekData: TimetableWeekDTO = TimetableWeekDTO(),
    @SerializedName("days")
    val daysData: List<TimetableDayDTO> = listOf(),
)

data class TimetableWeekDTO(
    @SerializedName("date_start")
    val dateStart: String = "",
    @SerializedName("date_end")
    val dateEnd: String = "",
    @SerializedName("is_odd")
    val isOdd: Boolean = false
)

data class TimetableDayDTO(
    @SerializedName("weekday")
    val weekday: Int = 0,
    @SerializedName("date")
    val date: String = "",
    @SerializedName("lessons")
    val lessonsData: List<TimetableLessonDTO> = listOf()
)

data class TimetableLessonDTO(
    @SerializedName("subject")
    val subject: String = "",
    @SerializedName("subject_short")
    val subjectShort: String = "",
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("additional_info")
    val additionalInfo: String = "",
    @SerializedName("time_start")
    val timeStart: String = "",
    @SerializedName("time_end")
    val timeEnd: String = "",
    @SerializedName("parity")
    val parity: Int = 0,
    @SerializedName("typeObj")
    val typeObj: TimetableLessonTypeDTO = TimetableLessonTypeDTO(),
    @SerializedName("teachers")
    val teachersList: List<TimetableTeacherDTO>? = listOf(),
    @SerializedName("auditories")
    val auditoriesList: List<TimetableAuditoriumDTO>? = listOf(),
    @SerializedName("webinar_url")
    val webinarUrl: String = "",
    @SerializedName("lms_url")
    val lmsUrl: String = "",
)

fun TimetableLessonDTO.toLesson(id: Long) = Lesson(
    id,
    timeStart,
    timeEnd,
    subject,
    typeObj.name,
    getAuditorium(),
    getTeacher(),
    lmsUrl
)

fun TimetableLessonDTO.getAuditorium(): String {
    auditoriesList?.let {
        if (auditoriesList.isNotEmpty()) {
            return auditoriesList.first().getFullName()
        }
    }
    return "Не знаю где"
}

fun TimetableLessonDTO.getTeacher(): String {
    teachersList?.let {
        if (teachersList.isNotEmpty()) {
            return teachersList.first().fullName
        }
    }
    return "Не знаю кто"
}

data class TimetableLessonTypeDTO(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("abbr")
    val abbr: String = ""
)

data class TimetableTeacherDTO(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("oid")
    val oid: Int = 0,
    @SerializedName("full_name")
    val fullName: String = "",
    @SerializedName("chair")
    val chair: String = ""
)

data class TimetableAuditoriumDTO(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("building")
    val building: TimetableAuditoriumBuildingDTO = TimetableAuditoriumBuildingDTO()
)

fun TimetableAuditoriumDTO.getFullName(): String = "${building.name}, $name"

data class TimetableAuditoriumBuildingDTO(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("abbr")
    val abbr: String = "",
    @SerializedName("address")
    val address: String = ""
)
