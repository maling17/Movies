package com.kresna.movies.di

import com.kresna.movies.api.MoviesApi
import com.kresna.movies.repository.popular.DefaultPopularRepository
import com.kresna.movies.repository.popular.PopularRepository
import com.kresna.movies.repository.upcoming.DefaultUpcomingRepository
import com.kresna.movies.repository.upcoming.UpcomingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object MovieRepositoryModule {


    @ViewModelScoped
    @Provides
    fun providesUpcomingRepository(api: MoviesApi): UpcomingRepository =
        DefaultUpcomingRepository(api)

    @ViewModelScoped
    @Provides
    fun providesPopularRepository(api: MoviesApi): PopularRepository = DefaultPopularRepository(api)
}