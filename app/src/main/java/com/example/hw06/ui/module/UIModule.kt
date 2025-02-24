package com.example.hw06.ui.module

import com.example.hw06.ui.screens.character.CharactersViewModel
import com.example.hw06.ui.screens.character.detail.CharacterDetailViewModel
import com.example.hw06.ui.screens.episode.EpisodesViewModel
import com.example.hw06.ui.screens.episode.detail.EpisodeDetailViewModel
import com.example.hw06.ui.screens.favorite.FavoriteViewModel
import com.example.hw06.ui.screens.location.LocationViewModel
import com.example.hw06.ui.screens.location.detail.LocationDetailViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule: Module = module {
    viewModel { CharactersViewModel(get()) }
    viewModel { LocationViewModel(get()) }
    viewModel { CharacterDetailViewModel(get()) }
    viewModel { LocationDetailViewModel(get()) }
    viewModel { EpisodesViewModel(get()) }
    viewModel { EpisodeDetailViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}