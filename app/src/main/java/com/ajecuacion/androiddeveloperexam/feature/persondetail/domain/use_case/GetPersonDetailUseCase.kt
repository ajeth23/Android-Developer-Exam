package com.ajecuacion.androiddeveloperexam.feature.persondetail.domain.use_case

import com.ajecuacion.androiddeveloperexam.feature.persondetail.domain.model.PersonDetail
import com.ajecuacion.androiddeveloperexam.feature.persondetail.domain.repository.PersonDetailRepository
import javax.inject.Inject

class GetPersonDetailUseCase @Inject constructor(
    private val repository: PersonDetailRepository
) {
    suspend operator fun invoke(id: String): PersonDetail? {
        return repository.getPersonDetail(id)
    }
}