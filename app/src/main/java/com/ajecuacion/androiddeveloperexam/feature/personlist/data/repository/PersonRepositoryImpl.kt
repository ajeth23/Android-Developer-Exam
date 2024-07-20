package com.ajecuacion.androiddeveloperexam.feature.personlist.data.repository

import android.content.Context
import com.ajecuacion.androiddeveloperexam.core.common.NetworkUtil
import com.ajecuacion.androiddeveloperexam.core.common.Resource
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.mapper.toPerson
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.mapper.toPersonEntity
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.source.local.PersonDao
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.source.remote.RandomUserApi
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.model.Person
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PersonRepositoryImpl(
    private val api: RandomUserApi,
    private val dao: PersonDao,
    private val context: Context
) : PersonRepository {

    override fun getPersons(): Flow<Resource<List<Person>>> = flow {
        emit(Resource.Loading())
        val persons = dao.getAllPersons().map { it.toPerson() }
        emit(Resource.Success(persons))

        if (!NetworkUtil.isNetworkAvailable(context) || !NetworkUtil.isInternetAvailable()) {
            emit(Resource.Error("Not connected to the internet"))
            return@flow
        }

        val response = api.getUsers(page = 1)
        if (response.isSuccessful) {
            response.body()?.results?.let { results ->
                dao.insertPersons(results.map { it.toPersonEntity() })
                emit(Resource.Success(dao.getAllPersons().map { it.toPerson() }))
            } ?: emit(Resource.Error("An unexpected error occurred"))
        } else {
            emit(Resource.Error("An unexpected error occurred"))
        }
    }

    override fun getPersonDetails(personId: String): Flow<Resource<Person>> = flow {
        emit(Resource.Loading())
        val person = dao.getPersonById(personId)?.toPerson()
        if (person != null) {
            emit(Resource.Success(person))
        } else {
            emit(Resource.Error("Person not found"))
        }
    }

    override fun refreshPersons(): Flow<Resource<List<Person>>> = flow {
        emit(Resource.Loading())

        val cachedPersons = dao.getAllPersons()
        if (cachedPersons.isNotEmpty()) {
            emit(Resource.Success(cachedPersons.map { it.toPerson() }))
        }

        if (!NetworkUtil.isNetworkAvailable(context) || !NetworkUtil.isInternetAvailable()) {
            emit(Resource.Error("Not connected to the internet"))
            return@flow
        }

        val response = api.getUsers(page = 1)
        if (response.isSuccessful) {
            response.body()?.results?.let { results ->
                dao.deleteAllPersons()
                dao.insertPersons(results.map { it.toPersonEntity() })
                emit(Resource.Success(dao.getAllPersons().map { it.toPerson() }))
            } ?: emit(Resource.Error("An unexpected error occurred"))
        } else {
            if (cachedPersons.isEmpty()) {
                emit(Resource.Error("Failed to load data: ${response.message()}"))
            }
        }
    }

    override fun loadMorePersons(page: Int): Flow<Resource<List<Person>>> = flow {
        emit(Resource.Loading())

        if (!NetworkUtil.isNetworkAvailable(context) || !NetworkUtil.isInternetAvailable()) {
            emit(Resource.Error("Not connected to the internet"))
            return@flow
        }

        val response = api.getUsers(page = page)
        if (response.isSuccessful) {
            response.body()?.results?.let { results ->
                dao.insertPersons(results.map { it.toPersonEntity() })
                emit(Resource.Success(dao.getAllPersons().map { it.toPerson() }))
            } ?: emit(Resource.Error("An unexpected error occurred"))
        } else {
            emit(Resource.Error("An unexpected error occurred"))
        }
    }
}
