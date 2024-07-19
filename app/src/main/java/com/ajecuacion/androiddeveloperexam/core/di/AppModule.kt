package com.ajecuacion.androiddeveloperexam.core.di

import android.content.Context
import androidx.room.Room
import com.ajecuacion.androiddeveloperexam.core.common.Constants
import com.ajecuacion.androiddeveloperexam.feature.persondetails.data.repository.PersonDetailRepositoryImpl
import com.ajecuacion.androiddeveloperexam.feature.persondetails.data.source.local.PersonDetailDao
import com.ajecuacion.androiddeveloperexam.feature.persondetails.data.source.local.PersonDetailDatabase
import com.ajecuacion.androiddeveloperexam.feature.persondetails.data.source.remote.PersonDetailApi
import com.ajecuacion.androiddeveloperexam.feature.persondetails.domain.repository.PersonDetailRepository
import com.ajecuacion.androiddeveloperexam.feature.persondetails.domain.usecase.GetPersonDetailUseCase
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.repository.PersonRepositoryImpl
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.source.local.AppDatabase
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.source.local.PersonDao
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.source.remote.RandomUserApi
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.repository.PersonRepository
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.use_case.GetPersonsUseCase
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.use_case.LoadMorePersonsUseCase
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.use_case.RefreshPersonsUseCase
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.use_case.UseCases

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Retrofit instance for person list and detail APIs
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Person List related dependencies
    @Provides
    @Singleton
    fun provideRandomUserApi(retrofit: Retrofit): RandomUserApi {
        return retrofit.create(RandomUserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun providePersonDao(appDatabase: AppDatabase): PersonDao {
        return appDatabase.personDao()
    }

    @Provides
    @Singleton
    fun providePersonRepository(
        api: RandomUserApi,
        dao: PersonDao
    ): PersonRepository {
        return PersonRepositoryImpl(api, dao)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: PersonRepository): UseCases {
        return UseCases(
            getPersonsUseCase = GetPersonsUseCase(repository),
            refreshPersonsUseCase = RefreshPersonsUseCase(repository),
            loadMorePersonsUseCase = LoadMorePersonsUseCase(repository)
        )
    }

    // Person Detail related dependencies
    @Provides
    @Singleton
    fun providePersonDetailApi(retrofit: Retrofit): PersonDetailApi {
        return retrofit.create(PersonDetailApi::class.java)
    }

    @Provides
    @Singleton
    fun providePersonDetailDatabase(@ApplicationContext context: Context): PersonDetailDatabase {
        return Room.databaseBuilder(
            context,
            PersonDetailDatabase::class.java,
            "person_detail_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providePersonDetailDao(database: PersonDetailDatabase): PersonDetailDao {
        return database.personDetailDao()
    }

    @Provides
    @Singleton
    fun providePersonDetailRepository(
        api: PersonDetailApi,
        dao: PersonDetailDao
    ): PersonDetailRepository {
        return PersonDetailRepositoryImpl(api, dao)
    }

    @Provides
    @Singleton
    fun provideGetPersonDetailUseCase(repository: PersonDetailRepository): GetPersonDetailUseCase {
        return GetPersonDetailUseCase(repository)
    }
}
