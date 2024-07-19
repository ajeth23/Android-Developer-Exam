package com.ajecuacion.androiddeveloperexam.feature.persondetails.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ajecuacion.androiddeveloperexam.feature.persondetails.data.model.PersonDetailEntity

@Dao
interface PersonDetailDao {
    @Query("SELECT * FROM person_details WHERE id = :id LIMIT 1")
    suspend fun getPersonDetail(id: String): PersonDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonDetail(personDetail: PersonDetailEntity)
}
