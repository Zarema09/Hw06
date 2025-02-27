package com.example.hw06.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.hw06.data.api.EpisodesApiService
import com.example.hw06.data.dto.episodes.EpisodesResponseDto
import com.example.hw06.ui.screens.episode.paging.EpisodePagingSource
import kotlinx.coroutines.flow.Flow

class EpisodeRepository(
    private val apiService: EpisodesApiService
) {

    private var currentFilters = Filters()

    data class Filters(
        val name: String? = null,
        val episode: String? = null
    )

    fun getEpisodesPager(): Flow<PagingData<EpisodesResponseDto.Episodes>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                EpisodePagingSource(apiService, currentFilters.name, currentFilters.episode)
            }
        ).flow
    }

    fun updateFilters(filters: Filters) {
        currentFilters = filters
    }

    fun resetFilters() {
        currentFilters = Filters()
    }

    suspend fun fetchEpisodeById(episodeId: Int): EpisodesResponseDto.Episodes {
        val response = apiService.fetchEpisodeById(episodeId)
        return if (response.isSuccessful) {
            response.body() ?: throw Exception("Episode not found")
        } else {
            throw Exception("Failed to load episode")
        }
    }
}