package com.spbstu.application.api

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.spbstu.application.domain.model.*
import com.spbstu.application.extensions.UNIX_TO_MILLS

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
                            Tag(tagIndex.toLong(), elem.toString())
                        }
                    list.add(help)
                }
                onItemsLoaded(list)
            }
    }

    fun sendHelp(title: String, description: String, link: String, tags: List<String>) {
        Firebase.firestore.collection(HELP_COLLECTION).add(
            Help.FirebaseEntity(
                title,
                System.currentTimeMillis() / UNIX_TO_MILLS,
                "Admin", //TODO: replace when user api resolved
                description,
                tags,
                link
            )
        )
    }
}