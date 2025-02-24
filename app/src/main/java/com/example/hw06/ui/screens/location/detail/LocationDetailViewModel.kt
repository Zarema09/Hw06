package com.example.hw06.ui.screens.location.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw06.data.dto.location.LocationResponseDto
import com.example.hw06.data.repository.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationDetailViewModel(private val repository: LocationRepository) : ViewModel() {

    private val _location = MutableStateFlow<LocationResponseDto.Location?>(null)
    val location: StateFlow<LocationResponseDto.Location?> get() = _location

    fun fetchLocationById(id: Int) {
        viewModelScope.launch {
            val response = repository.fetchLocationById(id)
            _location.value = response
        }
    }
}