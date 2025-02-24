package com.example.hw06.data.dto.character

data class CharacterResponseDto(
    val info: Info,
    val results: List<Character>
) {
    data class Info(
        val count: Int,
        val pages: Int,
        val next: String?,
        val prev: String?
    )

    data class Character(
        val id: Int,
        val name: String,
        val species: String,
        val image: String,
        val status: String
    )
}