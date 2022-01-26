package com.spbstu.application.ui.news

import androidx.lifecycle.viewModelScope
import com.spbstu.application.api.NewsApi
import com.spbstu.application.base.BaseViewModel
import com.spbstu.application.domain.model.ErrorState
import com.spbstu.application.domain.model.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsItemViewModel : BaseViewModel() {

    private val _stateData: MutableStateFlow<ErrorState> = MutableStateFlow(ErrorState.NONE)
    val stateData get() :StateFlow<ErrorState> = _stateData

    private val _newsData: MutableStateFlow<List<News>> = MutableStateFlow(listOf())
    val newsData get() :StateFlow<List<News>> = _newsData

    var currentPage = 1

    fun loadNews(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            NewsApi.getScienceList(url,
                currentPage++,
                _newsData.value.lastOrNull(),
                { _stateData.value = ErrorState.NetworkState }) {
                _newsData.value += it
            }
        }
    }
}