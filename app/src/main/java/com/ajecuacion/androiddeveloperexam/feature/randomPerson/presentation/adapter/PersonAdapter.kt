package com.ajecuacion.androiddeveloperexam.feature.randomPerson.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ajecuacion.androiddeveloperexam.feature.randomPerson.domain.model.Person
import com.ajecuacion.androiddeveloperexam.databinding.ItemPersonBinding
import com.ajecuacion.androiddeveloperexam.databinding.ItemLoadingBinding
import com.bumptech.glide.Glide

class PersonAdapter(private val onItemClickListener: OnItemClickListener) : ListAdapter<Person, RecyclerView.ViewHolder>(PersonDiffCallback()) {

    private var isLoading = false

    interface OnItemClickListener {
        fun onItemClick(person: Person)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE -> {
                val binding = ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PersonViewHolder(binding)
            }
            LOADING_VIEW_TYPE -> {
                val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LoadingViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PersonViewHolder) {
            val person = getItem(position)
            holder.bind(person)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoading && position == itemCount - 1) {
            LOADING_VIEW_TYPE
        } else {
            ITEM_VIEW_TYPE
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (isLoading) 1 else 0
    }

    fun showLoading(show: Boolean) {
        isLoading = show
        notifyDataSetChanged()
    }

    inner class PersonViewHolder(private val binding: ItemPersonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person) {
            binding.apply {
                name.text = "${person.firstName} ${person.lastName}"
                city.text = person.city
                Glide.with(root)
                    .load(person.profilePicture)
                    .into(profilePic)
                root.setOnClickListener {
                    onItemClickListener.onItemClick(person)
                }
            }
        }
    }

    inner class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val ITEM_VIEW_TYPE = 0
        private const val LOADING_VIEW_TYPE = 1
    }
}

