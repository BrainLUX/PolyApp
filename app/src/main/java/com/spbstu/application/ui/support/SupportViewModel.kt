package com.spbstu.application.ui.support

import androidx.lifecycle.viewModelScope
import com.spbstu.application.api.SupportApi
import com.spbstu.application.base.BaseViewModel
import com.spbstu.application.domain.model.Support
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SupportViewModel : BaseViewModel() {

    private val _supportData: MutableStateFlow<List<Support>> = MutableStateFlow(listOf())
    val supportData get() :StateFlow<List<Support>> = _supportData

    var fileLink: String? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            SupportApi.getSupportList {
                _supportData.value = it
            }
            SupportApi.getFileLink {
                fileLink = it
            }
        }
    }
}