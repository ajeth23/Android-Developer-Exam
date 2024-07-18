package com.ajecuacion.androiddeveloperexam.feature.persondetail.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person_details")
data class PersonDetailEntity(
    @PrimaryKey val id: String,
    val name: String,
    val city: String,
    val pictureUrl: String,
    val email: String,
    val phone: String
)
