package com.brainlux.polyapp.api

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.brainlux.polyapp.domain.model.ActualService
import com.brainlux.polyapp.domain.model.Service
import com.brainlux.polyapp.domain.model.ServiceCategory
import com.brainlux.polyapp.extensions.getValue

object FirebaseServicesApi {
    private const val ACTUAL_COLLECTION = "actual"

    fun getCategoryItems(serviceCategory: ServiceCategory, onItemsLoaded: (List<Service>) -> Unit) {
        Firebase.firestore.collection(serviceCategory.getValue())
            .get()
            .addOnSuccessListener { snapshot ->
                val list = mutableListOf<Service>()
                snapshot.forEachIndexed { index, documentSnapshot ->
                    val service = documentSnapshot.toObject(Service::class.java)
                    service.id = index.toLong()
                    service.serviceId = documentSnapshot.id
                    list.add(service)
                }
                onItemsLoaded(list)
            }
    }

    private fun getCategoryItem(
        actualService: ActualService,
        onItemLoaded: (Service) -> Unit
    ) {
        Firebase.firestore.document("${actualService.category}/${actualService.serviceId}")
            .get()
            .addOnSuccessListener { snapshot ->
                val service = snapshot.toObject(Service::class.java)
                service?.let {
                    service.serviceId = snapshot.id
                    onItemLoaded(service)
                }
            }
    }

    fun getActualServices(onItemsLoaded: (Service) -> Unit) {
        Firebase.firestore.collection(ACTUAL_COLLECTION)
            .get()
            .addOnSuccessListener { snapshot ->
                snapshot.forEach {
                    getCategoryItem(it.toObject(ActualService::class.java), onItemsLoaded)
                }
            }
    }
}