package com.spbstu.application.ui.timetable

import com.spbstu.application.api.timetable.TimetableApi
import com.spbstu.application.base.BaseViewModel
import com.spbstu.application.domain.model.Lesson
import com.spbstu.application.domain.model.toLesson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*

class TimetableViewModel : BaseViewModel() {

    private val _testDay: MutableStateFlow<MutableMap<Int, List<Lesson>>> =
        MutableStateFlow(hashMapOf())
    val testDay get() :StateFlow<MutableMap<Int, List<Lesson>>> = _testDay

    private val _pickData: MutableStateFlow<Calendar> =
        MutableStateFlow(Calendar.getInstance())
    val pickData: StateFlow<Calendar> = _pickData

    private val _stateData: MutableStateFlow<State> = MutableStateFlow(State.Loaded)
    val stateData get() :StateFlow<State> = _stateData

    private var queryCount = 0


    init {
        _pickData.value = Calendar.getInstance()
    }

    fun loadData(date: String) {
        val thisQuery = ++queryCount
        _testDay.value = hashMapOf()
        _stateData.value = State.Loading
        TimetableApi.getTimetable(date) {
            val tmpMap = hashMapOf<Int, List<Lesson>>()
            it?.let { timetableDTO ->
                timetableDTO.daysData.forEach { dayDto ->
                    val dayList = mutableListOf<Lesson>()
                    dayDto.lessonsData.forEachIndexed { index, elem ->
                        dayList.add(elem.toLesson(index.toLong()))
                    }
                    tmpMap[dayDto.weekday] = dayList
                }
                if (thisQuery == queryCount) {
                    _testDay.value = tmpMap
                    _stateData.value = State.Loaded
                }
            }
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

    fun updatePickedDate(calendar: Calendar) {
        _pickData.value = Calendar.getInstance().apply {
            set(Calendar.YEAR, calendar.get(Calendar.YEAR))
            set(Calendar.MONTH, calendar.get(Calendar.MONTH))
            set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH))
        }
    }

    sealed class State {
        object Loading : State()
        object Loaded : State()
    }
}
