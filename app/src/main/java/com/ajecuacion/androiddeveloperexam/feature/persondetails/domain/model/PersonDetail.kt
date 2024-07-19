package com.ajecuacion.androiddeveloperexam.feature.persondetails.domain.model

data class PersonDetail(
    val id: String,
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
