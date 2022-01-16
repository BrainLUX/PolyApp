package com.spbstu.application.ui.news_info

import androidx.lifecycle.viewModelScope
import com.spbstu.application.api.NewsApi
import com.spbstu.application.base.BaseViewModel
import com.spbstu.application.domain.model.ErrorState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsInfoViewModel : BaseViewModel() {

    private val _stateData: MutableStateFlow<ErrorState> = MutableStateFlow(ErrorState.NONE)
    val stateData get() :StateFlow<ErrorState> = _stateData

    private val _newsDescriptionData: MutableStateFlow<String> = MutableStateFlow("")
    val newsDescriptionData get() :StateFlow<String> = _newsDescriptionData

    fun loadDescription(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            NewsApi.getScienceDescription(url, { _stateData.value = ErrorState.NetworkState }) {
                _newsDescriptionData.value = it
            }
        }
    }
}