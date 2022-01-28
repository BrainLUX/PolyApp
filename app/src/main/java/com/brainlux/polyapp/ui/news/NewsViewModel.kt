package com.brainlux.polyapp.ui.news

import androidx.lifecycle.viewModelScope
import com.brainlux.polyapp.api.FirebaseNewsApi
import com.brainlux.polyapp.base.BaseViewModel
import com.brainlux.polyapp.domain.model.NewsTab
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel : BaseViewModel() {

    private val _newsData: MutableStateFlow<List<NewsTab>> = MutableStateFlow(listOf())
    val newsData get() :StateFlow<List<NewsTab>> = _newsData

    fun loadNews() {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseNewsApi.getNewsTabs {
                _newsData.value = it
            }
        }
    }
}