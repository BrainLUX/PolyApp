package com.spbstu.application.ui.timetable

import com.spbstu.application.base.BaseViewModel
import com.spbstu.application.domain.model.Lesson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*

class TimetableViewModel : BaseViewModel() {

    private val _testDay: MutableStateFlow<List<Lesson>> = MutableStateFlow(listOf())
    val testDay get() :StateFlow<List<Lesson>> = _testDay

    private val _pickData: MutableStateFlow<Calendar> =
        MutableStateFlow(Calendar.getInstance())
    val pickData: StateFlow<Calendar> = _pickData

    init {

        _pickData.value = Calendar.getInstance()

        _testDay.value =
            listOf(
                Lesson(
                    1,
                    "8:00",
                    "9:30",
                    "Матан",
                    "Практос",
                    "Дист",
                    "Кент"
                ),
                Lesson(
                    2,
                    "10:00",
                    "11:30",
                    "Физон",
                    "Лекция",
                    "Дист",
                    "Чел"
                )
            )
    }

    fun updatePickedDate(calendar: Calendar) {
        _pickData.value = Calendar.getInstance().apply {
            set(Calendar.YEAR, calendar.get(Calendar.YEAR))
            set(Calendar.MONTH, calendar.get(Calendar.MONTH))
            set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH))
        }
    }

    fun updateToNextWeek() {
        _pickData.value = Calendar.getInstance().apply {
            timeInMillis = pickData.value.timeInMillis
            set(Calendar.WEEK_OF_MONTH, pickData.value.get(Calendar.WEEK_OF_MONTH) + 1)
        }
    }

    fun updateToPrevWeek() {
        _pickData.value = Calendar.getInstance().apply {
            timeInMillis = pickData.value.timeInMillis
            set(Calendar.WEEK_OF_MONTH, pickData.value.get(Calendar.WEEK_OF_MONTH) - 1)
        }
    }

    fun updateToDays(day: Int) {
        _pickData.value = Calendar.getInstance().apply {
            timeInMillis = pickData.value.timeInMillis
            add(Calendar.DAY_OF_WEEK, day)
        }
    }
}
