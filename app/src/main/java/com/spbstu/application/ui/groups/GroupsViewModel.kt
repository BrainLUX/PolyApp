package com.spbstu.application.ui.groups

import androidx.lifecycle.viewModelScope
import com.spbstu.application.api.GroupsApi
import com.spbstu.application.base.BaseViewModel
import com.spbstu.application.domain.model.ErrorState
import com.spbstu.application.domain.model.Group
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