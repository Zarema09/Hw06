package com.example.hw06.ui.navigation

import CharacterScreen
import EpisodeScreen
import LocationScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hw06.ui.screens.character.CharactersViewModel
import com.example.hw06.ui.screens.character.detail.CharacterDetailScreen
import com.example.hw06.ui.screens.episode.detail.EpisodeDetailScreen
import com.example.hw06.ui.screens.favorite.FavoriteScreen
import com.example.hw06.ui.screens.favorite.FavoriteViewModel
import com.example.hw06.ui.screens.location.detail.LocationDetailScreen
import org.koin.androidx.compose.koinViewModel


@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val favoriteViewModel: FavoriteViewModel = koinViewModel() // Уже исправлено ранее
    val charactersViewModel: CharactersViewModel = koinViewModel() // Исправляем здесь

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Characters.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Characters.route) {
                CharacterScreen(navController, charactersViewModel, favoriteViewModel)
            }
            composable(BottomNavItem.Locations.route) {
                LocationScreen(navController)
            }
            composable(BottomNavItem.Episodes.route) {
                EpisodeScreen(navController)
            }
            composable(BottomNavItem.Favorite.route) {
                FavoriteScreen(viewModel = favoriteViewModel)
            }
            composable("character_detail/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                id?.let { CharacterDetailScreen(it) }
            }
            composable("location_detail/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                id?.let { LocationDetailScreen(it) }
            }
            composable("episode_detail/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                id?.let { EpisodeDetailScreen(it) }
            }
        }
    }
}