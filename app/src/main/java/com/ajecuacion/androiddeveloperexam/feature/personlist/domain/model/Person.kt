package com.ajecuacion.androiddeveloperexam.feature.personlist.domain.model

data class Person(
    val id: String,
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
