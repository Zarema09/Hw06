package com.example.hw06.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.hw06.data.api.LocationApiService
import com.example.hw06.data.dto.location.LocationResponseDto
import com.example.hw06.ui.screens.location.paging.LocationPagingSource
import kotlinx.coroutines.flow.Flow

class LocationRepository(
    private val apiService: LocationApiService
) {

    fun getLocationsPager(): Flow<PagingData<LocationResponseDto.Location>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { LocationPagingSource(apiService) }
        ).flow
    }

    suspend fun fetchLocationById(locationId: Int): LocationResponseDto.Location {
        val response = apiService.fetchLocationById(locationId)
        return if (response.isSuccessful) {
            response.body() ?: throw Exception("Location not found")
        } else {
            throw Exception("Failed to load location")
        }
    }
}