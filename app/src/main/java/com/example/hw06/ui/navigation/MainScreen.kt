package com.example.hw06.ui.navigation

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.hw06.data.local.FavoriteCharacterEntity
import com.example.hw06.ui.screens.character.CharacterScreen
import com.example.hw06.ui.screens.character.CharactersViewModel
import com.example.hw06.ui.screens.character.detail.CharacterDetailScreen
import com.example.hw06.ui.screens.episode.EpisodeScreen
import com.example.hw06.ui.screens.episode.detail.EpisodeDetailScreen
import com.example.hw06.ui.screens.favorite.FavoriteScreen
import com.example.hw06.ui.screens.favorite.FavoriteViewModel
import com.example.hw06.ui.screens.location.LocationScreen
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