package com.ajecuacion.androiddeveloperexam.feature.personlist.domain.usecase

import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.repository.PersonRepository
import javax.inject.Inject

class LoadMorePersonsUseCase @Inject constructor(private val repository: PersonRepository) {
    operator fun invoke(page: Int) = repository.loadMorePersons(page)
}
