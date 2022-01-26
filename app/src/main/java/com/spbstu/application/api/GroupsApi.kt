package com.spbstu.application.api

import com.spbstu.application.domain.model.Group
import org.json.JSONObject
import org.jsoup.Jsoup

object GroupsApi {

    fun getGroupsList(link: String, onError: () -> Unit, onItemsLoaded: (List<Group>) -> Unit) {
        val list = mutableListOf<Group>()
        try {
            val doc = Jsoup.connect(link).get()
            var json = doc.select("footer").next().html()
            json = json.substring(json.indexOf("{"))
            val data = JSONObject(json).getJSONObject("groups").getJSONObject("data")
            data.getJSONArray(data.keys().next())
                .let {
                    0.until(it.length()).mapIndexed { index, element ->
                        list.add(
                            Group(
                                index.toLong(),
                                it.optJSONObject(element).getString("name"),
                                it.optJSONObject(element).getString("id")
                            )
                        )
                    }
                }
            println("KEK")
            onItemsLoaded(list)
        } catch (e: Exception) {
            onError()
        }
    }
}