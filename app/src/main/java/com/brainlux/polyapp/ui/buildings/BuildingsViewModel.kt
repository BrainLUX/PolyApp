package com.brainlux.polyapp.ui.buildings

import com.brainlux.polyapp.api.FirebaseBuildingsApi
import com.brainlux.polyapp.base.BaseViewModel
import com.brainlux.polyapp.domain.model.Building
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