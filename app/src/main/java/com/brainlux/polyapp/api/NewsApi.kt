package com.brainlux.polyapp.api

import com.brainlux.polyapp.domain.model.News
import org.jsoup.Jsoup

object NewsApi {

    private const val CSS = "<style>img{width:100%}</style>"

    fun getScienceList(
        url: String,
        page: Int,
        startItem: News?,
        onError: () -> Unit,
        onItemsLoaded: (List<News>) -> Unit
    ) {
        val list = mutableListOf<News>()
        try {
            val doc = Jsoup.connect(url + page).get()
            var startId = 0L
            startItem?.let {
                startId = it.id + 1
            }
            doc.select(".cards-container .card")
                .forEachIndexed { index, element ->
                    list.add(
                        News(
                            startId + index.toLong(),
                            element.selectFirst(".card-title").html(),
                            element.selectFirst(".card-img-top").attr("src"),
                            element.selectFirst("a").attr("href")
                        )
                    )
                }
            onItemsLoaded(list)
        } catch (e: Exception) {
            onError()
        }
    }

    fun getScienceDescription(url: String, onError: () -> Unit, onItemsLoaded: (String) -> Unit) {
        try {
            val doc = Jsoup.connect(url).get()
            val result = doc.select(".article_content").html()
            onItemsLoaded(CSS + result)
        } catch (e: Exception) {
            onError()
        }
    }
}