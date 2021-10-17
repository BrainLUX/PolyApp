package com.spbstu.application.ui.building

import com.spbstu.application.api.FirebaseBuildingsApi
import com.spbstu.application.base.BaseViewModel
import com.spbstu.application.domain.model.Building
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