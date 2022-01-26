package com.spbstu.application.api

import com.spbstu.application.domain.model.Eat
import org.jsoup.Jsoup

object EatApi {
    private const val EAT_CAFE_URL = "https://umto.spbstu.ru/Kafe_bufet/"
    private const val EAT_CANTEEN_URL = "https://umto.spbstu.ru/Stolovye/"

    fun getEatList(onError: () -> Unit, onItemsLoaded: (List<Eat>) -> Unit) {
        try {
            val cafeList = parsePage(EAT_CAFE_URL, Eat.Type.CAFE)
            onItemsLoaded(
                (cafeList + parsePage(
                    EAT_CANTEEN_URL,
                    Eat.Type.CANTEEN, cafeList.size + 1
                )).shuffled()
            )
        } catch (e: Exception) {
            onError()
        }
    }

    private fun parsePage(
        url: String,
        type: Eat.Type,
        startIndex: Int = 0
    ): List<Eat> {
        val list = mutableListOf<Eat>()

        val doc = Jsoup.connect(url).get()
        doc.select(".content h2").forEachIndexed { index, element ->
            val titleElem = element.selectFirst("strong")
            if (titleElem != null) {
                val title = titleElem.text()
                val addressElem = element.nextElementSibling()
                if (addressElem.text().isNotEmpty()) {
                    val timeWeekElem = addressElem.nextElementSibling().nextElementSibling()
                    var time = ""
                    var imgLink = ""
                    if (timeWeekElem.tag().name == "h4") {
                        time = "${timeWeekElem.text()}, ${
                            timeWeekElem.nextElementSibling().text()
                        }"
                        var imgElem = timeWeekElem.nextElementSibling().nextElementSibling()
                            .selectFirst("img")
                        if (imgElem == null) {
                            imgElem = timeWeekElem.nextElementSibling().nextElementSibling()
                                .nextElementSibling()
                                .selectFirst("img")
                        }
                        imgElem?.let {
                            imgLink = it.attr("src")
                        }
                    } else {
                        var imgElem = addressElem.nextElementSibling().selectFirst("img")
                        if (imgElem == null) {
                            imgElem = addressElem.nextElementSibling().nextElementSibling()
                                .selectFirst("img")
                        }
                        imgElem?.let {
                            imgLink = it.attr("src")
                        }
                    }
                    list.add(
                        Eat(
                            (startIndex + index).toLong(),
                            title,
                            addressElem.text(),
                            time, imgLink, type
                        )
                    )
                }
            }
        }
        return list
    }
}