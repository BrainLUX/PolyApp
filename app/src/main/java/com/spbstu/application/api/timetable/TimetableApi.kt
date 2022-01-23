package com.spbstu.application.api.timetable

import com.spbstu.application.api.CallbackImpl
import com.spbstu.application.domain.model.TimetableDTO

object TimetableApi {
    private val tag = this::class.java.simpleName

    private val service: TimetableService =
        TimetableRetrofitClient.retrofit.create(TimetableService::class.java)

    fun getTimetable(date: String = "", onComplete: (TimetableDTO?) -> Unit) {
        service.getTimetable(date)
            .enqueue(CallbackImpl<TimetableDTO>(tag) { result, e ->
                if (e != null) {
                    return@CallbackImpl
                }
                onComplete(result)
            })
    }
}