package com.ajecuacion.androiddeveloperexam.feature.randomPerson.data.source.remote

import com.ajecuacion.androiddeveloperexam.feature.randomPerson.data.source.remote.dto.PersonDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {
    @GET("api/")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("results") results: Int = 10,
    ): Response<PersonDto>
}
