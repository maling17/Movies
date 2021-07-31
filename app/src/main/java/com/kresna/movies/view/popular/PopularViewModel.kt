package com.kresna.movies.view.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kresna.movies.db.MoviesDao
import com.kresna.movies.model.MoviesResponse
import com.kresna.movies.model.ResultsItem
import com.kresna.movies.repository.popular.PopularRepository
import com.kresna.movies.utils.Constant.Companion.API_KEY
import com.kresna.movies.utils.Constant.Companion.LANGUAGE
import com.kresna.movies.utils.Constant.Companion.POPULAR
import com.kresna.movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(val repository: PopularRepository) : ViewModel() {

    sealed class MoviesEvent {
        class Success(val moviesResponse: MoviesResponse) : MoviesEvent()
        class Failure(val errorText: String) : MoviesEvent()
        object Loading : MoviesEvent()
        object Empty : MoviesEvent()
    }

    init {
        popularMovies()
    }

    var popularPage = 0
    var popularResponse: MoviesResponse? = null
    val _popular = MutableStateFlow<MoviesEvent>(MoviesEvent.Empty)
    val popular: StateFlow<MoviesEvent> = _popular

  /*  fun getFavMovies() = viewModelScope.launch { MoviesDao.getAllMovies() }
    fun upsertMovies(resultsItem: ResultsItem) =
        viewModelScope.launch { repository.upsert(resultsItem) }

    fun deleteMovies(resultsItem: ResultsItem) =
        viewModelScope.launch { repository.deleteMovies(resultsItem) }
*/
    fun popularMovies() = viewModelScope.launch {
        popularPage += 1
        _popular.value = MoviesEvent.Loading
        when (val popularResponse =
            repository.getPopularMovie(API_KEY, LANGUAGE, POPULAR, popularPage)) {
            is Resource.Error -> {
                _popular.value = MoviesEvent.Failure(popularResponse.message!!)
            }
            is Resource.Success ->{
                val popularData = popularResponse.data
                _popular.value =MoviesEvent.Success(popularData!!)
            }
        }
    }

}