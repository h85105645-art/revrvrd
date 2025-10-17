package com.billieeilish.app.presentation.series

import androidx.lifecycle.ViewModel
import com.billieeilish.app.domain.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for series screen
 */
@HiltViewModel
class SeriesViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository
) : ViewModel() {
    
    // TODO: Implement series functionality
}