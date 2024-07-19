package com.ajecuacion.androiddeveloperexam.feature.persondetails.data.mapper

import com.ajecuacion.androiddeveloperexam.feature.persondetails.data.model.PersonDetailEntity
import com.ajecuacion.androiddeveloperexam.feature.persondetails.data.source.remote.dto.PersonDetailDto
import com.ajecuacion.androiddeveloperexam.feature.persondetails.domain.model.PersonDetail

fun PersonDetailDto.toEntity() = PersonDetailEntity(
    id = "${name.first}_${name.last}",
    firstName = name.first,
    lastName = name.last,
    dob = dob.date,
    age = dob.age,
    email = email,
    phone = phone,
    cell = cell,
    address = "${location.street.number} ${location.street.name}, ${location.city}, ${location.state}, ${location.country}",
    picture = picture.large
)

fun PersonDetailEntity.toDomain() = PersonDetail(
    id = id,
    firstName = firstName,
    lastName = lastName,
    dob = dob,
    age = age,
    email = email,
    phone = phone,
    cell = cell,
    address = address,
    picture = picture
)
