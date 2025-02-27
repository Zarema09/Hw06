package com.example.hw06.data.api

import com.example.hw06.data.dto.location.LocationResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationApiService {
    @GET("location")
    suspend fun fetchAllLocations(
        @Query("page") page: Int,
        @Query("name") name: String? = null,
        @Query("type") type: String? = null,
        @Query("dimension") dimension: String? = null
    ): LocationResponseDto

    @GET("location/{id}")
    suspend fun fetchLocationById(@Path("id") locationId: Int): Response<LocationResponseDto.Location>
}