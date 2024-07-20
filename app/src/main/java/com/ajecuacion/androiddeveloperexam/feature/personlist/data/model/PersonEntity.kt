package com.ajecuacion.androiddeveloperexam.feature.personlist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persons")
data class PersonEntity(
    @PrimaryKey val id: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val city: String,
    val profilePicture: String,
    val email: String,
    val phone: String,
    val cell: String,
    val dob: String,
    val age: Int,
    val street: String,
    val state: String,
    val country: String,
    val postcode: String,
    val contactPerson: String,
    val contactPersonPhone: String
)
