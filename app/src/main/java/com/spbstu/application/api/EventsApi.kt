package com.spbstu.application.api

import com.spbstu.application.domain.model.*
import org.jsoup.Jsoup

object EventsApi {
    private const val EVENT_URL = "https://www.spbstu.ru/media/announcements/"
    private const val BASE_URL = "https://www.spbstu.ru"

    fun getEventList(onItemsLoaded: (List<Event>) -> Unit) {
        val list = mutableListOf<Event>()

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
    }

    fun getEventDescription(link: String, onItemLoaded: (String) -> Unit) {
        val doc = Jsoup.connect(link).get()
        onItemLoaded(doc.select("#content_page").html())
    }
}