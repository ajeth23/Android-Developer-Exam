package com.ajecuacion.androiddeveloperexam.feature.personlist.domain.use_case

import com.ajecuacion.androiddeveloperexam.common.Resource
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.model.Person
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RefreshPersonsUseCase @Inject constructor(
    private val repository: PersonRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Person>>> {
        return repository.refreshData()
    }
}
