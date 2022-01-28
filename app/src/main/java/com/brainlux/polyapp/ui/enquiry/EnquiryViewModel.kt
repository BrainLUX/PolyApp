package com.brainlux.polyapp.ui.enquiry

import androidx.lifecycle.viewModelScope
import com.brainlux.polyapp.api.EnquiryApi
import com.brainlux.polyapp.base.BaseViewModel
import com.brainlux.polyapp.domain.model.Enquiry
import com.brainlux.polyapp.domain.model.ErrorState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EnquiryViewModel : BaseViewModel() {

    private val _stateData: MutableStateFlow<ErrorState> = MutableStateFlow(ErrorState.NONE)
    val stateData get() :StateFlow<ErrorState> = _stateData

    private val _enquiryData: MutableStateFlow<List<Enquiry>> = MutableStateFlow(listOf())
    val enquiryData get() :StateFlow<List<Enquiry>> = _enquiryData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            EnquiryApi.getEnquiryList({ _stateData.value = ErrorState.NetworkState }) {
                _enquiryData.value = it
            }
        }
    }
}