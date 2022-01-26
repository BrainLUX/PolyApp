package com.spbstu.application.ui.faculties

import androidx.lifecycle.viewModelScope
import com.spbstu.application.api.FacultyApi
import com.spbstu.application.base.BaseViewModel
import com.spbstu.application.domain.model.ErrorState
import com.spbstu.application.domain.model.Faculty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FacultiesViewModel : BaseViewModel() {

    private val _stateData: MutableStateFlow<ErrorState> = MutableStateFlow(ErrorState.NONE)
    val stateData get() :StateFlow<ErrorState> = _stateData

    private val _facultyData: MutableStateFlow<List<Faculty>> = MutableStateFlow(listOf())
    val facultyData get() :StateFlow<List<Faculty>> = _facultyData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            FacultyApi.getFacultiesList({ _stateData.value = ErrorState.NetworkState }) {
                _facultyData.value = it
            }
        }
    }
}