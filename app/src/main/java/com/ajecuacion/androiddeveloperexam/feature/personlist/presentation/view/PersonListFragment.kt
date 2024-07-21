package com.ajecuacion.androiddeveloperexam.feature.personlist.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ajecuacion.androiddeveloperexam.feature.main.MainActivityViewModel
import com.ajecuacion.androiddeveloperexam.R
import com.ajecuacion.androiddeveloperexam.core.common.Resource
import com.ajecuacion.androiddeveloperexam.databinding.FragmentPersonListBinding
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.model.Person
import com.ajecuacion.androiddeveloperexam.feature.personlist.presentation.adapter.PersonAdapter
import com.ajecuacion.androiddeveloperexam.feature.personlist.presentation.viewmodels.PersonViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonListFragment : Fragment(), PersonAdapter.OnItemClickListener {

    private val viewModel: PersonViewModel by viewModels()
    private val mainViewModel: MainActivityViewModel by activityViewModels()
    private var _binding: FragmentPersonListBinding? = null
    private val binding get() = _binding!!
    private lateinit var personAdapter: PersonAdapter

    private var wasOffline = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.persons.collectLatest { resource ->
                handleResource(resource)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.toastEvent.collect { message ->
                showSnackbar(message)
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshPersons()
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadMorePersons()
                }
            }
        })

        observeConnectivityChanges()
    }

    private fun setupRecyclerView() {
        personAdapter = PersonAdapter(this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = personAdapter
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun observeConnectivityChanges() {
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.connectivityStatus.collectLatest { isConnected ->
                if (isConnected && wasOffline) {
                    showSnackbar(getString(R.string.connected))
                    viewModel.getPersons() // Optionally refresh data when connected
                }
                wasOffline = !isConnected
            }
        }
    }

    private fun handleResource(resource: Resource<List<Person>>) {
        when (resource) {
            is Resource.Success -> {
                personAdapter.submitList(resource.data)
                personAdapter.showLoading(false)
                binding.swipeRefreshLayout.isRefreshing = false
                binding.progressBar.visibility = View.GONE
                binding.emptyView.visibility = if (resource.data.isNullOrEmpty()) View.VISIBLE else View.GONE
            }
            is Resource.Error -> {
                personAdapter.showLoading(false)
                binding.swipeRefreshLayout.isRefreshing = false
                binding.progressBar.visibility = View.GONE
                if (personAdapter.currentList.isEmpty()) {
                    binding.emptyView.visibility = View.VISIBLE
                }
                showSnackbar(resource.message ?: "An error occurred")
            }
            is Resource.Loading -> {
                if (viewModel.currentPage == 1) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    personAdapter.showLoading(true)
                }
                binding.emptyView.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(person: Person) {
        val action = PersonListFragmentDirections.actionPersonListFragmentToPersonDetailFragment(person.id)
        findNavController().navigate(action)
    }
}
