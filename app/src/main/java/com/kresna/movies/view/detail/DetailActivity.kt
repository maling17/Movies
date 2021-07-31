package com.kresna.movies.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kresna.movies.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val overview = intent.getStringExtra("overview")
        val date = intent.getStringExtra("date")
        val image = intent.getStringExtra("image")

        binding.tvDate.text = date
        binding.tvDesc.text = overview
        binding.tvTitle.text = title
        binding.ivBack.setOnClickListener { onBackPressed() }
        Picasso.get().load("https://api.themoviedb.org$image").into(binding.ivPoster)


    }
}