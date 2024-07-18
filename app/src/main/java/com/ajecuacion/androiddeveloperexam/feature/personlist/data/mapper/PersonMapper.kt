package com.ajecuacion.androiddeveloperexam.feature.personlist.data.mapper

import com.ajecuacion.androiddeveloperexam.feature.personlist.data.model.PersonEntity
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.model.Person


fun PersonEntity.toDomain(): Person {
    return Person(
        id = id,
        name = name,
        city = city,
        pictureUrl = pictureUrl
    )
}
