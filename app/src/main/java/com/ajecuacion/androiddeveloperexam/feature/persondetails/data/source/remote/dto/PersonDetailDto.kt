package com.ajecuacion.androiddeveloperexam.feature.persondetails.data.source.remote.dto

data class PersonDetailDto(
    val name: Name,
    val dob: Dob,
    val email: String,
    val phone: String,
    val cell: String,
    val location: Location,
    val picture: Picture
) {
    data class Name(val first: String, val last: String)
    data class Dob(val date: String, val age: Int)
    data class Location(val street: Street, val city: String, val state: String, val country: String) {
        data class Street(val number: Int, val name: String)
    }
    data class Picture(val large: String)
}
