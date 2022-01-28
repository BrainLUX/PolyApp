package com.brainlux.polyapp.ui.careers

import androidx.lifecycle.viewModelScope
import com.brainlux.polyapp.api.CareersApi
import com.brainlux.polyapp.base.BaseViewModel
import com.brainlux.polyapp.domain.model.Career
import com.brainlux.polyapp.domain.model.ErrorState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CareersViewModel : BaseViewModel() {

    private val _stateData: MutableStateFlow<ErrorState> = MutableStateFlow(ErrorState.NONE)
    val stateData get() :StateFlow<ErrorState> = _stateData

    private val _careersData: MutableStateFlow<List<Career>> = MutableStateFlow(listOf())
    val careersData get() :StateFlow<List<Career>> = _careersData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            CareersApi.getCareersList({ _stateData.value = ErrorState.NetworkState }) {
                _careersData.value = it
            }
        }
    }
}