package com.spbstu.application.ui.event_info

import androidx.lifecycle.viewModelScope
import com.spbstu.application.api.EventsApi
import com.spbstu.application.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventInfoViewModel : BaseViewModel() {

    private val _eventDescriptionData: MutableStateFlow<String> = MutableStateFlow("")
    val eventDescriptionData get() :StateFlow<String> = _eventDescriptionData

    fun loadDescription(link: String) {
        viewModelScope.launch(Dispatchers.IO) {
            EventsApi.getEventDescription(link,{}) {
                _eventDescriptionData.value = it
            }
        }
    }
}