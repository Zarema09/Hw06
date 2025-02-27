package com.example.hw06.ui.screens.location.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.hw06.data.api.LocationApiService
import com.example.hw06.data.dto.location.LocationResponseDto

class LocationPagingSource(
    private val apiService: LocationApiService,
    private val name: String? = null,
    private val type: String? = null,
    private val dimension: String? = null
) : PagingSource<Int, LocationResponseDto.Location>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationResponseDto.Location> {
        val position = params.key ?: 1
        return try {
            val response = apiService.fetchAllLocations(
                page = position,
                name = name,
                type = type,
                dimension = dimension
            )
            val locations = response.locationResults

            LoadResult.Page(
                data = locations,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (locations.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LocationResponseDto.Location>): Int? {
        return state.anchorPosition?.let { state.closestPageToPosition(it)?.prevKey?.plus(1) }
    }
}