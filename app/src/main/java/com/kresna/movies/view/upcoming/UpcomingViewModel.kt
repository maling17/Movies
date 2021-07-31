package com.kresna.movies.view.upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kresna.movies.model.MoviesResponse
import com.kresna.movies.repository.upcoming.UpcomingRepository
import com.kresna.movies.utils.Constant
import com.kresna.movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingViewModel @Inject constructor(val repository: UpcomingRepository) : ViewModel() {
    sealed class MoviesEvent {
        class Success(val moviesResponse: MoviesResponse) : MoviesEvent()
        class Failure(val errorText: String) : MoviesEvent()
        object Loading : MoviesEvent()
        object Empty : MoviesEvent()
    }

    init {
        upcomingMovies()
    }
    var upcomingPage = 0
    var upcomingResponse: MoviesResponse? = null
    val _upcoming = MutableStateFlow<MoviesEvent>(MoviesEvent.Empty)
    val upcoming: StateFlow<MoviesEvent> = _upcoming

  /*  fun getFavMovies() = viewModelScope.launch { repository.getMovie() }
    fun upsertMovies(resultsItem: ResultsItem) =
        viewModelScope.launch { repository.upsert(resultsItem) }

    fun deleteMovies(resultsItem: ResultsItem) =
        viewModelScope.launch { repository.deleteMovies(resultsItem) }
*/
    fun upcomingMovies() = viewModelScope.launch {

      upcomingPage+=1
        _upcoming.value = MoviesEvent.Loading
        when (val popularResponse =
            repository.getUpcomingMovies(
                Constant.API_KEY,
                Constant.LANGUAGE,
                Constant.POPULAR, upcomingPage)) {

            is Resource.Error -> {
                _upcoming.value = MoviesEvent.Failure(popularResponse.message!!)
            }
            is Resource.Success ->{
                val popularData = popularResponse.data
                _upcoming.value =MoviesEvent.Success(popularData!!)
            }
        }
    }
}