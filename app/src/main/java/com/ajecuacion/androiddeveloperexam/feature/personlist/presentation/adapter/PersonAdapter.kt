package com.ajecuacion.androiddeveloperexam.feature.personlist.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ajecuacion.androiddeveloperexam.databinding.ItemLoadingBinding
import com.ajecuacion.androiddeveloperexam.databinding.ItemPersonsBinding
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.model.Person
import com.bumptech.glide.Glide

private const val ITEM_TYPE_PERSON = 0
private const val ITEM_TYPE_LOADING = 1

class PersonAdapter(
    private val onPersonClick: (Person) -> Unit
) : ListAdapter<Person, RecyclerView.ViewHolder>(DiffCallback()) {

    private var isLoading = false

    override fun getItemViewType(position: Int): Int {
        return if (position == super.getItemCount() && isLoading) ITEM_TYPE_LOADING else ITEM_TYPE_PERSON
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_PERSON) {
            val binding =
                ItemPersonsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            PersonViewHolder(binding, onPersonClick)
        } else {
            val binding =
                ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PersonViewHolder) {
            holder.bind(getItem(position))
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (isLoading) 1 else 0
    }

    fun showLoading() {
        if (!isLoading) {
            isLoading = true
            notifyItemInserted(super.getItemCount())
        }
    }

    fun hideLoading() {
        if (isLoading) {
            isLoading = false
            notifyItemRemoved(super.getItemCount())
        }
    }

    class PersonViewHolder(
        private val binding: ItemPersonsBinding,
        private val onPersonClick: (Person) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person) {
            binding.name.text = person.name
            binding.city.text = person.city
            val pictureUrl = person.pictureUrl
            Glide.with(binding.profilePic.context)
                .load(pictureUrl)
                .into(binding.profilePic)

            binding.root.setOnClickListener {
                onPersonClick(person)
            }
        }
    }

    class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == newItem
        }
    }
}
