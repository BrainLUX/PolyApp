package com.brainlux.polyapp.ui.support

import androidx.lifecycle.viewModelScope
import com.brainlux.polyapp.api.SupportApi
import com.brainlux.polyapp.base.BaseViewModel
import com.brainlux.polyapp.domain.model.ErrorState
import com.brainlux.polyapp.domain.model.Support
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SupportViewModel : BaseViewModel() {

    private val _stateData: MutableStateFlow<ErrorState> = MutableStateFlow(ErrorState.NONE)
    val stateData get() :StateFlow<ErrorState> = _stateData

    private val _supportData: MutableStateFlow<List<Support>> = MutableStateFlow(listOf())
    val supportData get() :StateFlow<List<Support>> = _supportData

    var fileLink: String? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            SupportApi.getSupportList({ _stateData.value = ErrorState.NetworkState }) {
                _supportData.value = it
            }
            SupportApi.getFileLink({ _stateData.value = ErrorState.NetworkState }) {
                fileLink = it
            }
        }
    }
}