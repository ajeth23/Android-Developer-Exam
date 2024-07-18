package com.ajecuacion.androiddeveloperexam.feature.personlist.data.source.remote.dto

data class PersonResponse(
    val results: List<PersonDto>
)

data class PersonDto(
    val name: Name,
    val location: Location,
    val picture: Picture
)

data class Name(
    val first: String,
    val last: String
)

data class Location(
    val city: String?
)

data class Picture(
    val large: String?
)
