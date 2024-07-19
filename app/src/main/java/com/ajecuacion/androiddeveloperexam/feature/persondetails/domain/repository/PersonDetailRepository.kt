package com.ajecuacion.androiddeveloperexam.feature.persondetails.domain.repository

import com.ajecuacion.androiddeveloperexam.core.common.Resource
import com.ajecuacion.androiddeveloperexam.feature.persondetails.domain.model.PersonDetail
import kotlinx.coroutines.flow.Flow

interface PersonDetailRepository {
    fun getPersonDetail(id: String): Flow<Resource<PersonDetail>>
}
