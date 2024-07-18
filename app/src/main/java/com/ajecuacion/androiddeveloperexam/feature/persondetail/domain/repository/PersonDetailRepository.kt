package com.ajecuacion.androiddeveloperexam.feature.persondetail.domain.repository

import com.ajecuacion.androiddeveloperexam.feature.persondetail.domain.model.PersonDetail


interface PersonDetailRepository {
    suspend fun getPersonDetail(id: String): PersonDetail?
}