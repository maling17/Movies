package com.kresna.movies.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kresna.movies.R
import com.kresna.movies.adapter.MoviesPagerAdapter
import com.kresna.movies.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = MoviesPagerAdapter(supportFragmentManager)


    }
}