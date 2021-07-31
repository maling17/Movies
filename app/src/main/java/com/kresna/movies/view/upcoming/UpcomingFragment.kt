package com.kresna.movies.view.upcoming

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kresna.movies.adapter.MoviesAdapter
import com.kresna.movies.databinding.UpcomingFragmentBinding
import com.kresna.movies.view.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpcomingFragment : Fragment() {

    var _binding: UpcomingFragmentBinding? = null
    val binding get() = _binding!!
    val viewModel: UpcomingViewModel by viewModels()
    lateinit var adapterMovie: MoviesAdapter
    lateinit var bundle: Bundle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = UpcomingFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        lifecycleScope.launchWhenCreated {


            viewModel.upcoming.collect { event ->

                when (event) {
                    is UpcomingViewModel.MoviesEvent.Success -> {
                        binding.llShimmer.visibility = View.GONE
                        binding.rvUpcoming.visibility = View.VISIBLE
                        adapterMovie.differ.submitList(event.moviesResponse.results!!.toList())
                        Log.d("TAG", "${event.moviesResponse.results.toList()} ")

                    }
                    is UpcomingViewModel.MoviesEvent.Failure -> {
                        binding.llShimmer.visibility = View.GONE
                        binding.rvUpcoming.isVisible = true
                        Log.d("TAG", "onViewCreated: ${event.errorText} ")
                        Toast.makeText(requireContext(), event.errorText, Toast.LENGTH_SHORT)
                            .show()
                    }
                    is UpcomingViewModel.MoviesEvent.Loading -> {
                        binding.llShimmer.isVisible = true
                        binding.rvUpcoming.isVisible = false
                    }
                    else -> Unit
                }

            }
        }

    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                lifecycleScope.launch {
                    binding.pbUpcoming.visibility = View.VISIBLE
                    delay(1000L)
                    viewModel.upcomingMovies()
                    binding.pbUpcoming.visibility = View.GONE
                }

            }
        }
    }

    private fun setRecyclerView() {
        adapterMovie = MoviesAdapter()
        binding.rvUpcoming.apply {
            adapter = adapterMovie
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@UpcomingFragment.scrollListener)
        }
    }
}