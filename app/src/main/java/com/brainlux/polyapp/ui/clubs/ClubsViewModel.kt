package com.brainlux.polyapp.ui.clubs

import androidx.lifecycle.viewModelScope
import com.brainlux.polyapp.api.ClubsApi
import com.brainlux.polyapp.base.BaseViewModel
import com.brainlux.polyapp.domain.model.Club
import com.brainlux.polyapp.domain.model.ErrorState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClubsViewModel : BaseViewModel() {

    private val _stateData: MutableStateFlow<ErrorState> = MutableStateFlow(ErrorState.NONE)
    val stateData get() :StateFlow<ErrorState> = _stateData

    private val _clubsData: MutableStateFlow<List<Club>> = MutableStateFlow(listOf())
    val clubsData get() :StateFlow<List<Club>> = _clubsData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            ClubsApi.getClubsList({ _stateData.value = ErrorState.NetworkState }) {
                _clubsData.value = it
            }
        }
    }
}