package com.kresna.movies.repository.upcoming

import com.kresna.movies.db.MovieDatabase
import com.kresna.movies.db.MoviesDao
import com.kresna.movies.model.MoviesResponse
import com.kresna.movies.model.ResultsItem
import com.kresna.movies.utils.Resource

interface UpcomingRepository {


    suspend fun getUpcomingMovies(
        api_key: String,
        language: String,
        sort_by: String,
        page: Int
    ): Resource<MoviesResponse>


}