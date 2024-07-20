package com.ajecuacion.androiddeveloperexam.feature.personlist.data.source.remote.dto

data class Location(
    val city: String,
    val coordinates: Coordinates,
    val country: String,
    val postcode: String,
    val state: String,
    val street: Street,
    val timezone: Timezone
)