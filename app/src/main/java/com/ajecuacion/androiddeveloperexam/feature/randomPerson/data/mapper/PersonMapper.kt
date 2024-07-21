package com.ajecuacion.androiddeveloperexam.feature.randomPerson.data.mapper

import com.ajecuacion.androiddeveloperexam.feature.randomPerson.data.model.PersonEntity
import com.ajecuacion.androiddeveloperexam.feature.randomPerson.data.source.remote.dto.Result
import com.ajecuacion.androiddeveloperexam.feature.randomPerson.domain.model.Person

fun Result.toPersonEntity(): PersonEntity {
    return PersonEntity(
        id = this.login.uuid,
        username = this.login.username,
        firstName = this.name.first,
        lastName = this.name.last,
        city = this.location.city,
        profilePicture = this.picture.large,
        email = this.email,
        phone = this.phone,
        cell = this.cell,
        dob = this.dob.date,
        age = this.dob.age,
        street = "${this.location.street.number} ${this.location.street.name}",
        state = this.location.state,
        country = this.location.country,
        postcode = this.location.postcode,
        contactPerson = "John Doe", // Placeholder, adjust as needed
        contactPersonPhone = "123-456-7890" // Placeholder, adjust as needed
    )
}

fun PersonEntity.toPerson(): Person {
    return Person(
        id = this.id,
        username = this.username,
        firstName = this.firstName,
        lastName = this.lastName,
        city = this.city,
        profilePicture = this.profilePicture,
        email = this.email,
        phone = this.phone,
        cell = this.cell,
        dob = this.dob,
        age = this.age,
        street = this.street,
        state = this.state,
        country = this.country,
        postcode = this.postcode,
        contactPerson = this.contactPerson,
        contactPersonPhone = this.contactPersonPhone
    )
}
