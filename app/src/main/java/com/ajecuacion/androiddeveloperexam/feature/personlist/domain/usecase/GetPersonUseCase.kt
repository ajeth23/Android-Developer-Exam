package com.ajecuacion.androiddeveloperexam.feature.personlist.domain.usecase

import com.ajecuacion.androiddeveloperexam.core.common.Resource
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.model.Person
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.emitAll
import java.io.IOException
import javax.inject.Inject

class GetPersonUseCase @Inject constructor(private val repository: PersonRepository) {
    operator fun invoke(personId: String): Flow<Resource<Person>> = flow {
        emit(Resource.Loading())
        try {
            val person = repository.getPersonDetails(personId)
            emitAll(person)
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Check your internet connection"))
        }
    }
}
