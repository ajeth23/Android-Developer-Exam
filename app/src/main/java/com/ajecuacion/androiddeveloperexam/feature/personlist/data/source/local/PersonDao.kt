package com.ajecuacion.androiddeveloperexam.feature.personlist.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.model.PersonEntity

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersons(persons: List<PersonEntity>)

    @Query("SELECT * FROM persons")
    suspend fun getAllPersons(): List<PersonEntity>

    @Query("SELECT * FROM persons WHERE id = :personId")
    suspend fun getPersonById(personId: String): PersonEntity?

    @Query("DELETE FROM persons")
    suspend fun deleteAllPersons()
}
