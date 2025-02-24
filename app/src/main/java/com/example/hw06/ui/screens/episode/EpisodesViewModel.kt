package com.example.hw06.ui.screens.episode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.hw06.data.dto.episodes.EpisodesResponseDto
import com.example.hw06.data.repository.EpisodeRepository
import kotlinx.coroutines.flow.Flow

class EpisodesViewModel(
    private val repository: EpisodeRepository
) : ViewModel() {

    val episodesPager: Flow<PagingData<EpisodesResponseDto.Episodes>> =
        repository.getEpisodesPager().cachedIn(viewModelScope)
}