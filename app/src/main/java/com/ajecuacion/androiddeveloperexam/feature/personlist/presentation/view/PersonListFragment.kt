package com.ajecuacion.androiddeveloperexam.feature.personlist.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ajecuacion.androiddeveloperexam.core.common.Resource
import com.ajecuacion.androiddeveloperexam.databinding.FragmentPersonListBinding
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.model.Person
import com.ajecuacion.androiddeveloperexam.feature.personlist.presentation.adapter.PersonAdapter
import com.ajecuacion.androiddeveloperexam.feature.personlist.presentation.viewmodels.PersonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException

@AndroidEntryPoint
class PersonListFragment : Fragment(), PersonAdapter.OnItemClickListener {

    private val viewModel: PersonViewModel by viewModels()
    private var _binding: FragmentPersonListBinding? = null
    private val binding get() = _binding!!
    private lateinit var personAdapter: PersonAdapter

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

        lifecycleScope.launch {
            viewModel.persons.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        personAdapter.submitList(resource.data)
                        personAdapter.showLoading(false)
                        binding.swipeRefreshLayout.isRefreshing = false
                        binding.progressBar.visibility = View.GONE
                        binding.emptyView.visibility = if (resource.data!!.isEmpty()) View.VISIBLE else View.GONE
                    }
                    is Resource.Error -> {
                        personAdapter.showLoading(false)
                        binding.swipeRefreshLayout.isRefreshing = false
                        binding.progressBar.visibility = View.GONE
                        if (resource.message?.contains("internet", true) == true || resource.message?.contains("network", true) == true) {
                            Toast.makeText(context, "Not connected to the internet", Toast.LENGTH_SHORT).show()
                        }
                        binding.emptyView.visibility = View.VISIBLE
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
    }

    private fun setupRecyclerView() {
        personAdapter = PersonAdapter(this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = personAdapter
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
