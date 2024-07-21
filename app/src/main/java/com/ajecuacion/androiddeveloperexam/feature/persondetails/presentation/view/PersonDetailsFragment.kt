package com.ajecuacion.androiddeveloperexam.feature.persondetails.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.ajecuacion.androiddeveloperexam.core.common.Resource
import com.ajecuacion.androiddeveloperexam.databinding.FragmentPersonDetailsBinding
import com.ajecuacion.androiddeveloperexam.feature.persondetails.presentation.viewmodel.PersonDetailViewModel
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.model.Person
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class PersonDetailFragment : Fragment() {

    private val viewModel: PersonDetailViewModel by viewModels()
    private var _binding: FragmentPersonDetailsBinding? = null
    private val binding get() = _binding!!

    private var isTitleVisible = false
    private var personName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })

        val personId = arguments?.getString("personId") ?: return

        setupCollapsingToolbar()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadPersonDetail(personId)
                viewModel.personDetail.collect { resource ->
                    handleResource(resource)
                }
            }
        }
    }

    private fun handleResource(resource: Resource<Person>) {
        when (resource) {
            is Resource.Success -> {
                resource.data?.let { person ->
                    personName = "${person.firstName} ${person.lastName}"
                    binding.apply {
                        userName.text = "@${person.username}"
                        firstName.text = person.firstName
                        lastName.text = person.lastName
                        birthday.text = formatDate(person.dob)
                        age.text = calculateAge(person.dob).toString()
                        email.text = person.email
                        mobileNumber.text = person.phone
                        address.text =
                            "${person.street}, ${person.city}, ${person.state}, ${person.country} - ${person.postcode}"
                        contactPerson.text = person.contactPerson
                        contactPersonPhoneNumber.text = person.contactPersonPhone
                        Glide.with(profileImage)
                            .load(person.profilePicture)
                            .into(profileImage)
                    }
                }
            }

            is Resource.Error -> {
                Snackbar.make(
                    binding.root,
                    resource.message ?: "An error occurred",
                    Snackbar.LENGTH_LONG
                ).show()
                //error
            }

            is Resource.Loading -> {
                //loading
            }
        }
    }

    private fun setupCollapsingToolbar() {
        binding.appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val totalScrollRange = appBarLayout.totalScrollRange
            val isCollapsed = totalScrollRange + verticalOffset == 0

            if (isCollapsed && !isTitleVisible) {
                binding.collapsingToolbar.title = personName
                isTitleVisible = true
            } else if (!isCollapsed && isTitleVisible) {
                binding.collapsingToolbar.title = ""
                isTitleVisible = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun formatDate(date: String): String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val targetFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val dateObj = originalFormat.parse(date)
        return targetFormat.format(dateObj!!)
    }

    private fun calculateAge(dob: String): Int {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val dateObj = originalFormat.parse(dob)!!
        val calendar = Calendar.getInstance()
        val today = calendar.time
        val birthDate = Calendar.getInstance().apply { time = dateObj }

        var age = today.year - birthDate.time.year
        if (today.month < birthDate.time.month || (today.month == birthDate.time.month && today.date < birthDate.time.date)) {
            age--
        }
        return age
    }
}
