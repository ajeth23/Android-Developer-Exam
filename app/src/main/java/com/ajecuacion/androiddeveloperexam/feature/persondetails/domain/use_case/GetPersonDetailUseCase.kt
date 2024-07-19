package com.ajecuacion.androiddeveloperexam.feature.persondetails.domain.usecase

import com.ajecuacion.androiddeveloperexam.core.common.Resource
import com.ajecuacion.androiddeveloperexam.feature.persondetails.domain.model.PersonDetail
import com.ajecuacion.androiddeveloperexam.feature.persondetails.domain.repository.PersonDetailRepository
import kotlinx.coroutines.flow.Flow

class GetPersonDetailUseCase(private val repository: PersonDetailRepository) {
    operator fun invoke(id: String): Flow<Resource<PersonDetail>> {
        return repository.getPersonDetail(id)
    }
}
