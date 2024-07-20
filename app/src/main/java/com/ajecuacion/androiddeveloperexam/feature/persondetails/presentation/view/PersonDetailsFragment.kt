package com.ajecuacion.androiddeveloperexam.feature.persondetails.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ajecuacion.androiddeveloperexam.core.common.Resource
import com.ajecuacion.androiddeveloperexam.databinding.FragmentPersonDetailsBinding
import com.ajecuacion.androiddeveloperexam.feature.persondetails.presentation.viewmodel.PersonDetailViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PersonDetailFragment : Fragment() {

    private var _binding: FragmentPersonDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PersonDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val personId = arguments?.getString("person_id") ?: return
        viewModel.loadPersonDetail(personId)

        lifecycleScope.launchWhenStarted {
            viewModel.personDetail.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let { person ->
                            binding.apply {
                                firstName.text = person.firstName
                                lastName.text = person.lastName
                                birthday.text = person.dob
                                age.text = person.age.toString()
                                email.text = person.email
                                mobileNumber.text = person.cell
                                address.text = person.address
                                Glide.with(profileImage.context)
                                    .load(person.picture)
                                    .into(profileImage)
                            }
                            Log.d("PersonDetailFragment", "Data displayed: $person")
                        }
                    }
                    is Resource.Error -> {
                        Log.e("PersonDetailFragment", "Error: ${resource.message}")
                    }
                    is Resource.Loading -> {
                        Log.d("PersonDetailFragment", "Loading data...")
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
