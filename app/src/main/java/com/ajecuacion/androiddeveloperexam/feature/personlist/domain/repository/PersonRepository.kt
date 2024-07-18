package com.ajecuacion.androiddeveloperexam.feature.personlist.domain.repository

import androidx.lifecycle.LiveData
import com.ajecuacion.androiddeveloperexam.common.Resource
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.model.Person
import kotlinx.coroutines.flow.Flow

interface PersonRepository {
    fun getAllPersons(): LiveData<List<Person>>
    suspend fun refreshData(): Flow<Resource<List<Person>>>

    suspend fun loadMoreData(): Flow<Resource<List<Person>>>
}
