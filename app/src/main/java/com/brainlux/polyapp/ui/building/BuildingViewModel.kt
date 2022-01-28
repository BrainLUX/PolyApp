package com.brainlux.polyapp.ui.building

import com.brainlux.polyapp.api.FirebaseBuildingsApi
import com.brainlux.polyapp.base.BaseViewModel
import com.brainlux.polyapp.domain.model.Building
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BuildingViewModel : BaseViewModel() {

    private val _buildingActionData: MutableStateFlow<List<Building.Action>> =
        MutableStateFlow(listOf())
    val buildingActionData get() :StateFlow<List<Building.Action>> = _buildingActionData

    fun loadActions(id: String) {
        FirebaseBuildingsApi.getBuildingActions(id) {
            _buildingActionData.value = it
        }
    }
}