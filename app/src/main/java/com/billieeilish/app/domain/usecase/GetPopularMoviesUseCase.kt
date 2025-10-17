package com.billieeilish.app.domain.usecase

import com.billieeilish.app.domain.model.MovieResponse
import com.billieeilish.app.domain.repository.MovieRepository
import com.billieeilish.app.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for getting popular movies
 */
class GetPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(page: Int = 1): Flow<Resource<MovieResponse>> {
        return movieRepository.getPopularMovies(page)
    }
}