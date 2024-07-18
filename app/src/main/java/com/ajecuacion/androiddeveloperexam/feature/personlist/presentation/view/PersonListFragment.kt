package com.ajecuacion.androiddeveloperexam.feature.personlist.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ajecuacion.androiddeveloperexam.core.common.Resource
import com.ajecuacion.androiddeveloperexam.databinding.FragmentPersonListBinding
import com.ajecuacion.androiddeveloperexam.feature.personlist.presentation.adapter.PersonAdapter
import com.ajecuacion.androiddeveloperexam.feature.personlist.presentation.viewmodels.PersonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonListFragment : Fragment() {

    private var _binding: FragmentPersonListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PersonViewModel by viewModels()
    private lateinit var adapter: PersonAdapter

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

        viewModel.persons.observe(viewLifecycleOwner, Observer { persons ->
            adapter.submitList(persons.toMutableList())
        })

        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is Resource.Loading -> {
                    adapter.showLoading()
                }
                is Resource.Success -> {
                    adapter.hideLoading()
                }
                is Resource.Error -> {
                    adapter.hideLoading()
                }
            }
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadMoreData()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        adapter = PersonAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
