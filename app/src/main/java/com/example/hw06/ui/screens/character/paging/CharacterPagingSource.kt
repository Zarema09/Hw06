package com.example.hw06.ui.screens.character.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.hw06.data.api.CharacterApiService
import com.example.hw06.data.dto.character.CharacterResponseDto
import com.example.hw06.data.local.FavoriteCharacterEntity

class CharacterPagingSource(private val apiService: CharacterApiService) : PagingSource<Int, FavoriteCharacterEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FavoriteCharacterEntity> {
        val position = params.key ?: 1
        return try {
            val response = apiService.fetchAllCharacters(position)
            val characters = response.results.map { character : CharacterResponseDto.Character ->
                FavoriteCharacterEntity(
                    id = character.id,
                    name = character.name,
                    species = character.species,
                    image = character.image
                )
            }


            LoadResult.Page(
                data = characters,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (characters.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            Log.e("Paging", "Error loading characters", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, FavoriteCharacterEntity>): Int? {
        return state.anchorPosition?.let { state.closestPageToPosition(it)?.prevKey?.plus(1) }
    }
}