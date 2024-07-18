package com.ajecuacion.androiddeveloperexam.feature.personlist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person")
data class PersonEntity(
    @PrimaryKey val id: String,
    val name: String,
    val city: String,
    val pictureUrl: String
)
