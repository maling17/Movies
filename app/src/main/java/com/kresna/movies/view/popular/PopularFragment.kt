package com.kresna.movies.view.popular

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
import com.kresna.movies.adapter.MoviesAdapter
import com.kresna.movies.databinding.PopularFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PopularFragment : Fragment() {

    val viewModel: PopularViewModel by viewModels()
    lateinit var moviesAdapter: MoviesAdapter
    var _binding: PopularFragmentBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PopularFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        lifecycleScope.launchWhenCreated {

            viewModel.popular.collect { event ->

                when (event) {
                    is PopularViewModel.MoviesEvent.Success -> {
                        binding.llShimmer.isVisible = false
                        binding.rvPopular.isVisible = true

                        moviesAdapter.differ.submitList(event.moviesResponse.results!!.toList())
                        Log.d("TAG", "onViewCreated: BERHASIL ")

                    }
                    is PopularViewModel.MoviesEvent.Failure -> {
                        binding.llShimmer.isVisible = false
                        binding.rvPopular.isVisible = true
                        Log.d("TAG", "onViewCreated: ${event.errorText} ")
                        Toast.makeText(requireContext(), event.errorText, Toast.LENGTH_SHORT)
                            .show()
                    }
                    is PopularViewModel.MoviesEvent.Loading -> {
                        binding.llShimmer.isVisible = true
                        binding.rvPopular.isVisible = false
                    }
                    else -> Unit
                }

            }
        }

    }

    private fun setRecyclerView() {
        moviesAdapter = MoviesAdapter()
        binding.rvPopular.apply {
            adapter = moviesAdapter
            layoutManager = LinearLayoutManager(activity)
//            addOnScrollListener(this@UpcomingFragment.scrollListener)
        }
    }
}