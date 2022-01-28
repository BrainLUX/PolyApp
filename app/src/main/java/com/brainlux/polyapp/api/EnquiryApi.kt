package com.brainlux.polyapp.api

import com.brainlux.polyapp.domain.model.Enquiry
import org.jsoup.Jsoup

object EnquiryApi {
    private const val ENQUIRY_URL = "https://www.spbstu.ru/students/enquiry/"

    fun getEnquiryList(onError: () -> Unit, onItemsLoaded: (List<Enquiry>) -> Unit) {
        val list = mutableListOf<Enquiry>()
        try {
            val doc = Jsoup.connect(ENQUIRY_URL).get()
            doc.select(".panel-group .panel")
                .forEachIndexed { index, element ->
                    val titleElem = element.selectFirst(".accordion-toggle")
                    val descElem = element.selectFirst(".panel-body")
                    list.add(
                        Enquiry(
                            index.toLong(),
                            titleElem.text(),
                            descElem.html().replace("<form", "<form style='display:none;'")
                        )
                    )
                }
            onItemsLoaded(list)
        } catch (e: Exception) {
            onError()
        }
    }
}