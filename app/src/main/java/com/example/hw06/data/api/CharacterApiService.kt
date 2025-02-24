package com.example.hw06.data.api

import com.example.hw06.data.dto.character.CharacterResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApiService {

    @GET("character")
    suspend fun fetchAllCharacters(
        @Query("page") page: Int
    ): CharacterResponseDto

    @GET("character/{id}")
    suspend fun fetchCharacterById(@Path("id") characterId: Int): CharacterResponseDto
}