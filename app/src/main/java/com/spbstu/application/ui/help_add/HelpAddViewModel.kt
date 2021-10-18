package com.spbstu.application.ui.help_add

import com.spbstu.application.api.FirebaseHelpApi
import com.spbstu.application.base.BaseViewModel
import com.spbstu.application.domain.model.Help
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HelpAddViewModel : BaseViewModel() {

    private val _helpTagData: MutableStateFlow<List<Help.Tag>> = MutableStateFlow(listOf())
    val helpTagData get() :StateFlow<List<Help.Tag>> = _helpTagData

    fun setTags(list: List<Help.Tag>) {
        _helpTagData.value = list
    }

    fun sendData(title: String, description: String, link: String, tags: List<String>) =
        FirebaseHelpApi.sendHelp(title, description, link, tags)

    fun toggleTag(tag: Help.Tag) {
        _helpTagData.value = helpTagData.value.map {
            if (it.id == tag.id) {
                it.copy(isChecked = !it.isChecked)
            } else {
                it.copy()
            }
        }
    }
}