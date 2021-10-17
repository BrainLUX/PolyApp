package com.spbstu.application.api

import com.spbstu.application.domain.model.*
import org.jsoup.Jsoup

object SupportApi {
    private const val SUPPORT_URL = "http://profunion.pro/materialnaya-pomosh-spbpu"

    fun getSupportList(onItemsLoaded: (List<Support>) -> Unit) {
        val list = mutableListOf<Support>()

        val doc = Jsoup.connect(SUPPORT_URL).get()
        doc.select(".t613__middle_item")
            .forEachIndexed { index, element ->
                val rows = element.select(".t-descr")
                list.add(
                    Support(
                        index.toLong(),
                        rows[0].selectFirst("strong").text(),
                        rows[1].text(),
                        rows[2].html(),
                        rows[3].html()
                    )
                )
            }
        onItemsLoaded(list)
    }

    fun getFileLink(onItemLoaded: (String) -> Unit) {
        val doc = Jsoup.connect(SUPPORT_URL).get()
        onItemLoaded(doc.selectFirst("a.t-btn").attr("href"))
    }
}