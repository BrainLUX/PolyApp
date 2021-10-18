package com.spbstu.application.api

import com.spbstu.application.domain.model.Club
import com.spbstu.application.domain.model.Tag
import org.jsoup.Jsoup

object ClubsApi {
    private const val CLUBS_URL = "https://www.spbstu.ru/studorg/"

    fun getClubsList(onError: () -> Unit, onItemsLoaded: (List<Club>) -> Unit) {
        val list = mutableListOf<Club>()
        try {
            val doc = Jsoup.connect(CLUBS_URL).get()
            doc.select(".student-org__info")
                .forEachIndexed { index, element ->
                    list.add(
                        Club(
                            index.toLong(),
                            element.selectFirst(".name").html(),
                            element.selectFirst(".student-org__type").text(),
                            element.selectFirst(".student-org__desc").text(),
                            element.select(".student-org__field")
                                .mapIndexed { elemIndex, elem ->
                                    Tag(elemIndex.toLong(), elem.text())
                                },
                            element.select(".student-org__social-item")
                                .mapIndexed { elemIndex, elem ->
                                    Club.Link(
                                        elemIndex.toLong(),
                                        elem.attr("title"),
                                        elem.attr("href")
                                    )
                                }
                        )
                    )
                }
            onItemsLoaded(list)
        } catch (e: Exception) {
            onError()
        }
    }
}