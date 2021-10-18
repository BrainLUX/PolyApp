package com.spbstu.application.ui.help

import com.spbstu.application.api.FirebaseHelpApi
import com.spbstu.application.base.BaseViewModel
import com.spbstu.application.domain.model.Help
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HelpViewModel : BaseViewModel() {

    private val _helpData: MutableStateFlow<List<Help>> = MutableStateFlow(listOf())
    val helpData get() :StateFlow<List<Help>> = _helpData

    init {
        FirebaseHelpApi.getHelp {
            _helpData.value = it
        }
    }
}