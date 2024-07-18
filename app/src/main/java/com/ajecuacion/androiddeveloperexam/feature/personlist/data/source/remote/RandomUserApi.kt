package com.ajecuacion.androiddeveloperexam.feature.personlist.data.source.remote

import com.ajecuacion.androiddeveloperexam.feature.personlist.data.source.remote.dto.PersonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {
    @GET("?inc=picture,name,location")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("results") results: Int = 10,
        @Query("seed") seed: String = "default"
    ): Response<PersonResponse>
}
