package com.spbstu.application.ui.careers

import androidx.lifecycle.viewModelScope
import com.spbstu.application.api.CareersApi
import com.spbstu.application.base.BaseViewModel
import com.spbstu.application.domain.model.Career
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CareersViewModel : BaseViewModel() {

    private val _careersData: MutableStateFlow<List<Career>> = MutableStateFlow(listOf())
    val careersData get() :StateFlow<List<Career>> = _careersData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            CareersApi.getCareersList {
                _careersData.value = it
            }
        }
    }
}