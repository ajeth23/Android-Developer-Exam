package com.ajecuacion.androiddeveloperexam.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ajecuacion.androiddeveloperexam.core.common.ConnectivityMonitor
import com.ajecuacion.androiddeveloperexam.core.common.Constants
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.repository.PersonRepositoryImpl
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.source.local.AppDatabase
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.source.local.PersonDao
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.source.remote.RandomUserApi
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.repository.PersonRepository
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.usecase.GetPersonUseCase
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.usecase.GetPersonsUseCase
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.usecase.LoadMorePersonsUseCase
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.usecase.RefreshPersonsUseCase
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.usecase.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideConnectivityMonitor(@ApplicationContext context: Context): ConnectivityMonitor {
        return ConnectivityMonitor(context)
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideRandomUserApi(retrofit: Retrofit): RandomUserApi =
        retrofit.create(RandomUserApi::class.java)

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app_database"
    ).build()

    @Provides
    @Singleton
    fun providePersonDao(appDatabase: AppDatabase) = appDatabase.personDao()

    @Provides
    @Singleton
    fun providePersonRepository(
        api: RandomUserApi,
        dao: PersonDao,
        context: Context
    ): PersonRepository = PersonRepositoryImpl(api, dao, context)

    @Provides
    @Singleton
    fun provideUseCases(repository: PersonRepository) = UseCases(
        getPersonsUseCase = GetPersonsUseCase(repository),
        refreshPersonsUseCase = RefreshPersonsUseCase(repository),
        loadMorePersonsUseCase = LoadMorePersonsUseCase(repository),
        getPersonDetailsUseCase = GetPersonUseCase(repository)
    )
}

