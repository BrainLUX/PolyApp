package com.brainlux.polyapp.api.timetable

import com.brainlux.polyapp.domain.model.TimetableDTO
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