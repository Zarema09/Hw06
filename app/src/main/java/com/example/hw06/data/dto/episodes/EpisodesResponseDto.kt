package com.example.hw06.data.dto.episodes

data class EpisodesResponseDto(
    val episodesResults : List<Episodes>
){
    data class Episodes(
        val id: Int,
        val name: String,
        val airDate: String,
        val episode: String,
        val characters: List<String>,
        val url: String,
        val created: String
    )
}