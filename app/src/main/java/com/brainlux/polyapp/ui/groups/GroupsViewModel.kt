package com.brainlux.polyapp.ui.groups

import androidx.lifecycle.viewModelScope
import com.brainlux.polyapp.api.GroupsApi
import com.brainlux.polyapp.base.BaseViewModel
import com.brainlux.polyapp.domain.model.ErrorState
import com.brainlux.polyapp.domain.model.Group
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GroupsViewModel : BaseViewModel() {

    private val _stateData: MutableStateFlow<ErrorState> = MutableStateFlow(ErrorState.NONE)
    val stateData get() :StateFlow<ErrorState> = _stateData

    private val _groupsData: MutableStateFlow<List<Group>?> = MutableStateFlow(null)
    val groupsData get() :StateFlow<List<Group>?> = _groupsData

    var link: String = "https://ruz.spbstu.ru/faculty/95/groups/"

    init {
        viewModelScope.launch(Dispatchers.IO) {
            GroupsApi.getGroupsList(link, { _stateData.value = ErrorState.NetworkState }) {
                _groupsData.value = it
            }
        }
    }
}