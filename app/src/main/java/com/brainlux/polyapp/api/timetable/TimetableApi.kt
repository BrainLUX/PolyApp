package com.brainlux.polyapp.api.timetable

import com.brainlux.polyapp.api.CallbackImpl
import com.brainlux.polyapp.domain.model.TimetableDTO

object TimetableApi {
    private val tag = this::class.java.simpleName

    private val service: TimetableService =
        TimetableRetrofitClient.retrofit.create(TimetableService::class.java)

    fun getTimetable(
        group: String,
        date: String,
        onComplete: (TimetableDTO?) -> Unit
    ) {
        service.getTimetable(group, date)
            .enqueue(CallbackImpl<TimetableDTO>(tag) { result, e ->
                if (e != null) {
                    return@CallbackImpl
                }
                onComplete(result)
            })
    }
}