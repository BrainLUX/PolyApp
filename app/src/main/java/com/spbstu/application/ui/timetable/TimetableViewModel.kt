package com.spbstu.application.ui.timetable

import com.spbstu.application.base.BaseViewModel
import com.spbstu.application.domain.model.Lesson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TimetableViewModel : BaseViewModel() {

    private val _testDay: MutableStateFlow<List<Lesson>> = MutableStateFlow(listOf())
    val testDay get() :StateFlow<List<Lesson>> = _testDay

    init {
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
}
