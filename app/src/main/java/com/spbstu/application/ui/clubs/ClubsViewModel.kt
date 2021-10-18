package com.spbstu.application.ui.clubs

import androidx.lifecycle.viewModelScope
import com.spbstu.application.api.ClubsApi
import com.spbstu.application.base.BaseViewModel
import com.spbstu.application.domain.model.Club
import com.spbstu.application.domain.model.ErrorState
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