package com.brainlux.polyapp.ui.services

import com.brainlux.polyapp.api.FirebaseServicesApi
import com.brainlux.polyapp.base.BaseViewModel
import com.brainlux.polyapp.domain.model.Service
import com.brainlux.polyapp.domain.model.ServiceCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ServicesViewModel : BaseViewModel() {

    private val _actualData: MutableStateFlow<List<Service>> = MutableStateFlow(listOf())
    val actualData get() :StateFlow<List<Service>> = _actualData

    private val _universityData: MutableStateFlow<List<Service>> = MutableStateFlow(listOf())
    val universityData get() :StateFlow<List<Service>> = _universityData

    private val _studentData: MutableStateFlow<List<Service>> = MutableStateFlow(listOf())
    val studentData get() :StateFlow<List<Service>> = _studentData

    private val _otherData: MutableStateFlow<List<Service>> = MutableStateFlow(listOf())
    val otherData get() :StateFlow<List<Service>> = _otherData

    init {
        FirebaseServicesApi.getActualServices {
            _actualData.value += listOf(it)
        }
        FirebaseServicesApi.getCategoryItems(ServiceCategory.UNIVERSITY) {
            _universityData.value = it
        }
        FirebaseServicesApi.getCategoryItems(ServiceCategory.STUDENT) {
            _studentData.value = it
        }
        FirebaseServicesApi.getCategoryItems(ServiceCategory.OTHER) {
            _otherData.value = it
        }
    }
}