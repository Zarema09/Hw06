package com.example.hw06.ui.screens.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.hw06.data.dto.location.LocationResponseDto
import com.example.hw06.data.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class LocationViewModel(
    private val repository: LocationRepository
)  : ViewModel() {

    val locationPager : Flow<PagingData<LocationResponseDto.Location>> =
        repository.getLocationsPager().cachedIn(viewModelScope)
}