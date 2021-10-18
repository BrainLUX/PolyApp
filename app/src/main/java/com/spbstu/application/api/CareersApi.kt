package com.spbstu.application.api

import com.spbstu.application.domain.model.*
import org.jsoup.Jsoup

object CareersApi {
    private const val CAREERS_URL = "https://dep.spbstu.ru/stazghirovki/"

    fun getCareersList(onItemsLoaded: (List<Career>) -> Unit) {
        val list = mutableListOf<Career>()

        val doc = Jsoup.connect(CAREERS_URL).get()
        doc.select(".accordion > li")
            .forEachIndexed { index, element ->
                val linkElem = element.selectFirst(".a-list-block a")
                var link = ""
                linkElem?.let {
                    link = it.attr("href")
                }

                list.add(
                    Career(
                        index.toLong(),
                        element.selectFirst(".title > p").html(),
                        element.selectFirst(".desc > p").html(),
                        link
                    )
                )
            }
        onItemsLoaded(list)
    }
}