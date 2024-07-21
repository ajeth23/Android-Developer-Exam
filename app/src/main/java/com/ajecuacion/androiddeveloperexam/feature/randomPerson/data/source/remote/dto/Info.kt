package com.ajecuacion.androiddeveloperexam.feature.randomPerson.data.source.remote.dto

data class Info(
    val page: Int,
    val results: Int,
    val seed: String,
    val version: String
)