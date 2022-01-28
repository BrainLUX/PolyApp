package com.brainlux.polyapp.api

import com.brainlux.polyapp.domain.model.Faculty
import org.jsoup.Jsoup

object FacultyApi {
    private const val FACULTY_URL = "https://ruz.spbstu.ru"

    fun getFacultiesList(onError: () -> Unit, onItemsLoaded: (List<Faculty>) -> Unit) {
        val list = mutableListOf<Faculty>()
        try {
            val doc = Jsoup.connect(FACULTY_URL).get()
            doc.select(".faculty-list__link")
                .forEachIndexed { index, element ->
                    list.add(
                        Faculty(
                            index.toLong(),
                            element.text(),
                            FACULTY_URL + element.attr("href")
                        )
                    )
                }
            onItemsLoaded(list)
        } catch (e: Exception) {
            onError()
        }
    }
}