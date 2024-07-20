package com.ajecuacion.androiddeveloperexam.feature.persondetails.data.repository

import android.util.Log
import com.ajecuacion.androiddeveloperexam.core.common.Resource
import com.ajecuacion.androiddeveloperexam.feature.persondetails.data.mapper.toDomain
import com.ajecuacion.androiddeveloperexam.feature.persondetails.data.mapper.toEntity
import com.ajecuacion.androiddeveloperexam.feature.persondetails.data.source.local.PersonDetailDao
import com.ajecuacion.androiddeveloperexam.feature.persondetails.data.source.remote.PersonDetailApi
import com.ajecuacion.androiddeveloperexam.feature.persondetails.domain.model.PersonDetail
import com.ajecuacion.androiddeveloperexam.feature.persondetails.domain.repository.PersonDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class PersonDetailRepositoryImpl(
    private val api: PersonDetailApi,
    private val dao: PersonDetailDao
) : PersonDetailRepository {

    override fun getPersonDetail(id: String): Flow<Resource<PersonDetail>> = flow {
        emit(Resource.Loading())

        val personDetail = dao.getPersonDetail(id)?.toDomain()
        if (personDetail != null) {
            Log.d("PersonDetailRepository", "Loaded from database: $personDetail")
            emit(Resource.Success(personDetail))
        } else {
            try {
                val response = api.getPersonDetail(id)
                if (response.isSuccessful) {
                    response.body()?.let {
                        val entity = it.toEntity()
                        dao.insertPersonDetail(entity)
                        Log.d("PersonDetailRepository", "Fetched from API and saved to database: $entity")
                        emit(Resource.Success(entity.toDomain()))
                    } ?: emit(Resource.Error("Unknown error"))
                } else {
                    emit(Resource.Error("Failed to load data: ${response.message()}"))
                }
            } catch (e: IOException) {
                emit(Resource.Error("Failed to load data: ${e.message}"))
            } catch (e: HttpException) {
                emit(Resource.Error("Failed to load data: ${e.message}"))
            }
        }
    }
}
