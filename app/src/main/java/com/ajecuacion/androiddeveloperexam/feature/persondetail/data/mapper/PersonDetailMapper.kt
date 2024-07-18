package com.ajecuacion.androiddeveloperexam.feature.persondetail.data.mapper

import com.ajecuacion.androiddeveloperexam.feature.persondetail.data.model.PersonDetailEntity
import com.ajecuacion.androiddeveloperexam.feature.persondetail.data.source.remote.dto.PersonDetailDto
import com.ajecuacion.androiddeveloperexam.feature.persondetail.domain.model.PersonDetail

fun PersonDetailDto.toEntity(): PersonDetailEntity {
    return PersonDetailEntity(
        id = id,
        name = name,
        city = city,
        pictureUrl = pictureUrl,
        email = email,
        phone = phone
    )
}

fun PersonDetailEntity.toDomain(): PersonDetail {
    return PersonDetail(
        id = id,
        name = name,
        city = city,
        pictureUrl = pictureUrl,
        email = email,
        phone = phone
    )
}
