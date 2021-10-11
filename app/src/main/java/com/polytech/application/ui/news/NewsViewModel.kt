package com.polytech.application.ui.news

import com.polytech.application.base.BaseViewModel
import com.polytech.application.templates.TemplateModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NewsViewModel : BaseViewModel() {

    private val _data: MutableStateFlow<List<TemplateModel>> = MutableStateFlow(listOf())
    val data get() :StateFlow<List<TemplateModel>> = _data

    init {
        _data.value = listOf(
            TemplateModel(0, "Test"),
            TemplateModel(1, "Test"),
            TemplateModel(2, "Test"),
            TemplateModel(3, "Test")
        )
    }
}