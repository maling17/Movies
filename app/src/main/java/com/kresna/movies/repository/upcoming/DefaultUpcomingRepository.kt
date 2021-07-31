package com.kresna.movies.repository.upcoming

import com.kresna.movies.api.MoviesApi
import com.kresna.movies.db.MoviesDao
import com.kresna.movies.model.MoviesResponse
import com.kresna.movies.model.ResultsItem
import com.kresna.movies.utils.Constant
import com.kresna.movies.utils.Resource
import javax.inject.Inject

class DefaultUpcomingRepository @Inject constructor(val api: MoviesApi) :
    UpcomingRepository {
    override suspend fun getUpcomingMovies(
        api_key: String,
        language: String,
        sort_by: String,
        page: Int
    ): Resource<MoviesResponse> {
        return try {
            val response = api.getMovies(
                Constant.API_KEY,
                Constant.LANGUAGE, Constant.UPCOMING, page
            )
            val result = response.body()

            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error Occured")
        }
    }


}