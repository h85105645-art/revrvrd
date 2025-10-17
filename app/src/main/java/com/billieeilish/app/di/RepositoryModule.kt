package com.billieeilish.app.di

import com.billieeilish.app.data.repository.IptvRepositoryImpl
import com.billieeilish.app.data.repository.MovieRepositoryImpl
import com.billieeilish.app.data.repository.SeriesRepositoryImpl
import com.billieeilish.app.domain.repository.IptvRepository
import com.billieeilish.app.domain.repository.MovieRepository
import com.billieeilish.app.domain.repository.SeriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module for repository bindings
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository
    
    @Binds
    @Singleton
    abstract fun bindSeriesRepository(
        seriesRepositoryImpl: SeriesRepositoryImpl
    ): SeriesRepository
    
    @Binds
    @Singleton
    abstract fun bindIptvRepository(
        iptvRepositoryImpl: IptvRepositoryImpl
    ): IptvRepository
}