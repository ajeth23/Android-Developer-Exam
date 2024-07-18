package com.ajecuacion.androiddeveloperexam.feature.personlist.domain.use_case

import androidx.lifecycle.LiveData
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.model.Person
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.repository.PersonRepository
import javax.inject.Inject

class GetPersonsUseCase @Inject constructor(
    private val repository: PersonRepository
) {
    operator fun invoke(): LiveData<List<Person>> {
        return repository.getAllPersons()
    }
}
