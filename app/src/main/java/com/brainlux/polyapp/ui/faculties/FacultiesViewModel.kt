package com.brainlux.polyapp.ui.faculties

import androidx.lifecycle.viewModelScope
import com.brainlux.polyapp.api.FacultyApi
import com.brainlux.polyapp.base.BaseViewModel
import com.brainlux.polyapp.domain.model.ErrorState
import com.brainlux.polyapp.domain.model.Faculty
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