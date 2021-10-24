package com.spbstu.application.ui.enquiry

import androidx.lifecycle.viewModelScope
import com.spbstu.application.api.EnquiryApi
import com.spbstu.application.base.BaseViewModel
import com.spbstu.application.domain.model.Enquiry
import com.spbstu.application.domain.model.ErrorState
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