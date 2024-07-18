package com.ajecuacion.androiddeveloperexam.feature.personlist.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.ajecuacion.androiddeveloperexam.common.Resource
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.source.remote.RandomUserApi
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.source.local.PersonDao
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.mapper.toDomain
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.model.PersonEntity
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.model.Person
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(
    private val api: RandomUserApi,
    private val dao: PersonDao
) : PersonRepository {

    private var currentPage = 1

    override fun getAllPersons(): LiveData<List<Person>> {
        return dao.getAllPersons().map { entities ->
            Log.d("PersonRepository", "Retrieved ${entities.size} persons from database")
            entities.map { it.toDomain() }
        }
    }

    override suspend fun refreshData(): Flow<Resource<List<Person>>> = flow {
        emit(Resource.Loading())

        // Check if cache is available
        val cachedPersons = dao.getAllPersons().value
        if (!cachedPersons.isNullOrEmpty()) {
            emit(Resource.Success(cachedPersons.map { it.toDomain() }))
        } else {
            // Fetch from remote if cache is not available
            try {
                val response = api.getUsers(page = 1)
                if (response.isSuccessful && response.body() != null) {
                    val persons = response.body()!!.results.map {
                        PersonEntity(
                            id = it.name.first + it.name.last,
                            name = "${it.name.first} ${it.name.last}",
                            city = it.location.city ?: "Unknown City",
                            pictureUrl = it.picture.large ?: ""
                        )
                    }
                    dao.clear()
                    dao.insertAll(persons)
                    Log.d("PersonRepository", "Inserted ${persons.size} persons into database")
                    currentPage = 1
                    emit(Resource.Success(persons.map { it.toDomain() }))
                } else {
                    emit(Resource.Error("Failed to load data: ${response.message()}"))
                }
            } catch (e: Exception) {
                emit(Resource.Error("Failed to load data: ${e.message}"))
            }
        }
    }

    override suspend fun loadMoreData(): Flow<Resource<List<Person>>> = flow {
        emit(Resource.Loading())

        try {
            val response = api.getUsers(page = currentPage + 1)
            if (response.isSuccessful && response.body() != null) {
                val persons = response.body()!!.results.map {
                    PersonEntity(
                        id = it.name.first + it.name.last,
                        name = "${it.name.first} ${it.name.last}",
                        city = it.location.city ?: "Unknown City",
                        pictureUrl = it.picture.large ?: ""
                    )
                }
                dao.insertAll(persons)
                currentPage++
                emit(Resource.Success(persons.map { it.toDomain() }))
            } else {
                emit(Resource.Error("Failed to load more data: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Failed to load more data: ${e.message}"))
        }
    }
}
