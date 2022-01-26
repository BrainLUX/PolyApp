package com.spbstu.application.api.timetable

import com.spbstu.application.domain.model.TimetableDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TimetableService {
    @GET("/api/v1/ruz/scheduler/{group}")
    fun getTimetable(
        @Path("group") group: String,
        @Query("date") date: String
    ): Call<TimetableDTO>
}