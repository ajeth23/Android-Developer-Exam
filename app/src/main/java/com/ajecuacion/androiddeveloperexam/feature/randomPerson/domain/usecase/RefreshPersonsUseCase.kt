package com.ajecuacion.androiddeveloperexam.feature.randomPerson.domain.usecase

import com.ajecuacion.androiddeveloperexam.core.common.Resource
import com.ajecuacion.androiddeveloperexam.feature.randomPerson.domain.model.Person
import com.ajecuacion.androiddeveloperexam.feature.randomPerson.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class RefreshPersonsUseCase @Inject constructor(
    private val repository: PersonRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Person>>> = flow {
        emit(Resource.Loading())
        try {
            val persons = repository.refreshPersons()
            emitAll(persons)
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Check your internet connection"))
        }
    }
}
