package com.ajecuacion.androiddeveloperexam.feature.persondetails.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person_details")
data class PersonDetailEntity(
    @PrimaryKey val id: String,
    val firstName: String,
    val lastName: String,
    val dob: String,
    val age: Int,
    val email: String,
    val phone: String,
    val cell: String,
    val address: String,
    val picture: String
)
