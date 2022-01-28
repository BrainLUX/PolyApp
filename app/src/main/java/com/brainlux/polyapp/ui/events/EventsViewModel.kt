package com.brainlux.polyapp.ui.events

import androidx.lifecycle.viewModelScope
import com.brainlux.polyapp.api.EventsApi
import com.brainlux.polyapp.base.BaseViewModel
import com.brainlux.polyapp.domain.model.ErrorState
import com.brainlux.polyapp.domain.model.Event
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