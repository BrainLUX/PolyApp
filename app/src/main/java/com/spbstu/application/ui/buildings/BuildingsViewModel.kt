package com.spbstu.application.ui.buildings

import com.spbstu.application.api.FirebaseBuildingsApi
import com.spbstu.application.base.BaseViewModel
import com.spbstu.application.domain.model.Building
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BuildingsViewModel : BaseViewModel() {

    private val _buildingsData: MutableStateFlow<List<Building>> = MutableStateFlow(listOf())
    val buildingsData get() :StateFlow<List<Building>> = _buildingsData

    init {
        FirebaseBuildingsApi.getBuildings {
            _buildingsData.value = it
        }
    }
}