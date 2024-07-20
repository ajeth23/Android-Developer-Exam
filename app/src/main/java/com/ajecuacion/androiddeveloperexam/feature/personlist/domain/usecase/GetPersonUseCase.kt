package com.ajecuacion.androiddeveloperexam.feature.personlist.domain.usecase

import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.repository.PersonRepository
import javax.inject.Inject

class GetPersonUseCase @Inject constructor(private val repository: PersonRepository) {
    operator fun invoke(personId: String) = repository.getPersonDetails(personId)
}
