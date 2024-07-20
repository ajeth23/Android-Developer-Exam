package com.ajecuacion.androiddeveloperexam.feature.persondetails.data.source.remote

import com.ajecuacion.androiddeveloperexam.feature.persondetails.data.source.remote.dto.PersonDetailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PersonDetailApi {
    @GET("api/person/{id}")
    suspend fun getPersonDetail(@Path("id") id: String): Response<PersonDetailDto>
}
