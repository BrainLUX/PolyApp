package com.brainlux.polyapp.ui.eat

import androidx.lifecycle.viewModelScope
import com.brainlux.polyapp.api.EatApi
import com.brainlux.polyapp.base.BaseViewModel
import com.brainlux.polyapp.domain.model.Eat
import com.brainlux.polyapp.domain.model.ErrorState
import com.brainlux.polyapp.domain.model.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EatViewModel : BaseViewModel() {

    private val _stateData: MutableStateFlow<ErrorState> = MutableStateFlow(ErrorState.NONE)
    val stateData get() :StateFlow<ErrorState> = _stateData

    private val _eatData: MutableStateFlow<List<Eat>> = MutableStateFlow(listOf())
    val eatData get() :StateFlow<List<Eat>> = _eatData

    private val _tagData: MutableStateFlow<List<Tag>> = MutableStateFlow(listOf())
    val tagData get() :StateFlow<List<Tag>> = _tagData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            EatApi.getEatList({
                _stateData.value = ErrorState.NetworkState
            }) {
                _eatData.value = it
            }
        }
    }

    fun setTags(list: List<Tag>) {
        _tagData.value = list
        if (tagData.value.isNotEmpty()) {
            toggleTag(tagData.value.first())
        }
    }

    fun toggleTag(tag: Tag) {
        _tagData.value = _tagData.value.map {
            if (it.id == tag.id) {
                it.copy(isChecked = true)
            } else {
                it.copy(isChecked = false)
            }
        }
    }

    fun getToggledTag(): Tag? = _tagData.value.firstOrNull { it.isChecked }

}