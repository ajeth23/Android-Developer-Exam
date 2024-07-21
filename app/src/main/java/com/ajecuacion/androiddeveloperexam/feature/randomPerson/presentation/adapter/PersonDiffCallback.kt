package com.ajecuacion.androiddeveloperexam.feature.randomPerson.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ajecuacion.androiddeveloperexam.feature.randomPerson.domain.model.Person

class PersonDiffCallback : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem == newItem
    }
}
