package com.ajecuacion.androiddeveloperexam.feature.persondetail.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ajecuacion.androiddeveloperexam.feature.persondetail.data.model.PersonDetailEntity


@Dao
interface PersonDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonDetail(personDetail: PersonDetailEntity)

    @Query("SELECT * FROM person_details WHERE id = :id")
    suspend fun getPersonDetailById(id: String): PersonDetailEntity?
}
