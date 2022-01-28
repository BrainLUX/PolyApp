package com.brainlux.polyapp.api

import com.brainlux.polyapp.domain.model.Support
import org.jsoup.Jsoup

object SupportApi {
    private const val SUPPORT_URL = "http://profunion.pro/materialnaya-pomosh-spbpu"

    fun getSupportList(onError: () -> Unit, onItemsLoaded: (List<Support>) -> Unit) {
        val list = mutableListOf<Support>()
        try {
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
        } catch (e: Exception) {
            onError()
        }
    }

    fun getFileLink(onError: () -> Unit, onItemLoaded: (String) -> Unit) {
        try {
            val doc = Jsoup.connect(SUPPORT_URL).get()
            onItemLoaded(doc.selectFirst("a.t-btn").attr("href"))
        } catch (e: Exception) {
            onError()
        }
    }
}