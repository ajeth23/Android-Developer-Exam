package com.ajecuacion.androiddeveloperexam.feature.personlist.domain.repository

import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.model.Person
import com.ajecuacion.androiddeveloperexam.core.common.Resource
import kotlinx.coroutines.flow.Flow

interface PersonRepository {
    fun getPersons(): Flow<Resource<List<Person>>>
    fun getPersonDetails(personId: String): Flow<Resource<Person>>
    fun refreshPersons(): Flow<Resource<List<Person>>>  // Updated return type
    fun loadMorePersons(page: Int): Flow<Resource<List<Person>>>
}
