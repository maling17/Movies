package com.kresna.movies.api

import com.kresna.movies.model.MoviesResponse
import com.kresna.movies.utils.Constant.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("/3/discover/movie")
    suspend fun getMovies(
        @Query("api_key")api_key:String,
        @Query("language")language:String,
        @Query("sort_by")sort_by:String,
        @Query("page")page:Int
    ):Response<MoviesResponse>
}