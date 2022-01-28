package com.brainlux.polyapp.api

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.brainlux.polyapp.domain.model.*

object FirebaseBuildingsApi {
    private const val BUILDINGS_COLLECTION = "buildings"
    private const val BUILDING_ACTIONS_COLLECTION = "actions"

    fun getBuildings(onItemsLoaded: (List<Building>) -> Unit) {
        Firebase.firestore.collection(BUILDINGS_COLLECTION)
            .get()
            .addOnSuccessListener { snapshot ->
                val list = mutableListOf<Building>()
                snapshot.forEachIndexed { index, documentSnapshot ->
                    val building = documentSnapshot.toObject(Building::class.java)
                    building.id = index.toLong()
                    building.buildingId = documentSnapshot.id
                    list.add(building)
                }
                onItemsLoaded(list)
            }
    }

    fun getBuildingActions(id: String, onItemsLoaded: (List<Building.Action>) -> Unit) {
        Firebase.firestore.collection("$BUILDINGS_COLLECTION/$id/$BUILDING_ACTIONS_COLLECTION")
            .get()
            .addOnSuccessListener { snapshot ->
                val list = mutableListOf<Building.Action>()
                snapshot.forEachIndexed { index, documentSnapshot ->
                    val action = documentSnapshot.toObject(Building.Action::class.java)
                    action.id = index.toLong()
                    list.add(action)
                }
                onItemsLoaded(list)
            }
    }
}