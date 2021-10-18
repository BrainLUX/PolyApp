package com.spbstu.application.api

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.spbstu.application.domain.model.*

object FirebaseHelpApi {
    private const val HELP_COLLECTION = "help"
    private const val TAGS_FIELD = "tags"

    fun getHelp(onItemsLoaded: (List<Help>) -> Unit) {
        Firebase.firestore.collection(HELP_COLLECTION)
            .get()
            .addOnSuccessListener { snapshot ->
                val list = mutableListOf<Help>()
                snapshot.forEachIndexed { index, documentSnapshot ->
                    val help = documentSnapshot.toObject(Help::class.java)
                    help.id = index.toLong()
                    help.helpId = documentSnapshot.id
                    help.tagList =
                        (documentSnapshot[TAGS_FIELD] as List<*>).mapIndexed { tagIndex, elem ->
                            Help.Tag(
                                tagIndex.toLong(),
                                elem.toString()
                            )
                        }
                    list.add(help)
                }
                onItemsLoaded(list)
            }
    }
}