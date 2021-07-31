package com.kresna.movies.repository.popular

import com.kresna.movies.api.MoviesApi
import com.kresna.movies.db.MoviesDao
import com.kresna.movies.model.MoviesResponse
import com.kresna.movies.model.ResultsItem
import com.kresna.movies.utils.Constant.Companion.API_KEY
import com.kresna.movies.utils.Constant.Companion.LANGUAGE
import com.kresna.movies.utils.Constant.Companion.POPULAR
import com.kresna.movies.utils.Resource
import javax.inject.Inject

class DefaultPopularRepository @Inject constructor(
    val api: MoviesApi,

) : PopularRepository {
    override suspend fun getPopularMovie(
        api_key: String,
        language: String,
        sort_by: String,
        page: Int
    ): Resource<MoviesResponse> {

        return try {
            val response = api.getMovies(
                API_KEY,
                LANGUAGE, POPULAR, page
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