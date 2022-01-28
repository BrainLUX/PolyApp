package com.brainlux.polyapp.api

import com.brainlux.polyapp.domain.model.Event
import org.jsoup.Jsoup

object EventsApi {
    private const val BASE_URL = "https://www.spbstu.ru"
    private const val EVENT_URL = "$BASE_URL/media/announcements/"

    fun getEventList(onError: () -> Unit, onItemsLoaded: (List<Event>) -> Unit) {
        val list = mutableListOf<Event>()
        try {
            val doc = Jsoup.connect(EVENT_URL).get()
            doc.select(".event-box")
                .forEachIndexed { index, element ->
                    val endElem = element.selectFirst(".e-edge-end")
                    var ends = ""
                    endElem?.let {
                        ends = it.text()
                    }
                    list.add(
                        Event(
                            index.toLong(),
                            element.selectFirst(".event-header").text(),
                            BASE_URL + element.selectFirst("img").attr("src"),
                            element.selectFirst(".e-edge-start").text(),
                            ends,
                            BASE_URL + element.selectFirst(".event-header").attr("href")
                        )
                    )
                }
            onItemsLoaded(list)
        } catch (e: Exception) {
            onError()
        }
    }

    fun getEventDescription(link: String,onError: () -> Unit, onItemLoaded: (String) -> Unit) {
        try {
            val doc = Jsoup.connect(link).get()
            onItemLoaded(doc.select("#content_page").html())
        } catch (e: Exception) {
            onError()
        }
    }
}