package com.ajecuacion.androiddeveloperexam.feature.personlist.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.model.PersonEntity

@Dao
interface PersonDao {
    @Query("SELECT * FROM person")
    fun getAllPersons(): LiveData<List<PersonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(persons: List<PersonEntity>)

    @Query("DELETE FROM person")
    suspend fun clear()
}
