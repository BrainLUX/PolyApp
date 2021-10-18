package com.spbstu.application.ui.events

import androidx.lifecycle.viewModelScope
import com.spbstu.application.api.EventsApi
import com.spbstu.application.base.BaseViewModel
import com.spbstu.application.domain.model.ErrorState
import com.spbstu.application.domain.model.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventsViewModel : BaseViewModel() {

    private val _stateData: MutableStateFlow<ErrorState> = MutableStateFlow(ErrorState.NONE)
    val stateData get() :StateFlow<ErrorState> = _stateData

    private val _eventsData: MutableStateFlow<List<Event>> = MutableStateFlow(listOf())
    val eventsData get() :StateFlow<List<Event>> = _eventsData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            EventsApi.getEventList({ _stateData.value = ErrorState.NetworkState }) {
                _eventsData.value = it
            }
        }
    }
}