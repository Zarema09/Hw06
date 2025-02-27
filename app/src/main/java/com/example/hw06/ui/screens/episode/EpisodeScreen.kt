import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.hw06.data.dto.episodes.EpisodesResponseDto
import com.example.hw06.ui.screens.episode.EpisodesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EpisodeScreen(
    navController: NavHostController,
    viewModel: EpisodesViewModel = koinViewModel()
) {
    val episodes = viewModel.episodesPager.collectAsLazyPagingItems()
    val filters = viewModel.filters.value
    var showFilterDialog by remember { mutableStateOf(false) }
    var tempName by remember { mutableStateOf(filters.name ?: "") }
    var tempEpisode by remember { mutableStateOf(filters.episode) }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = tempName,
            onValueChange = { tempName = it },
            label = { Text("Search by name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { showFilterDialog = true }) {
                Text("Filters")
            }
            Button(onClick = {
                viewModel.resetFilters()
                tempName = ""
                tempEpisode = null
                episodes.refresh()
            }) {
                Text("Reset")
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(count = episodes.itemCount) { index ->
                val episode = episodes[index]
                episode?.let {
                    EpisodeItem(episode = it) {
                        navController.navigate("episode_detail/${it.id}")
                    }
                }
            }

            episodes.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    loadState.refresh is LoadState.Error -> {
                        item {
                            val e = loadState.refresh as LoadState.Error
                            Text(
                                text = "Ошибка загрузки: ${e.error.localizedMessage}",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        item {
                            val e = loadState.append as LoadState.Error
                            Text(
                                text = "Ошибка загрузки дополнительных эпизодов: ${e.error.localizedMessage}",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    if (showFilterDialog) {
        AlertDialog(
            onDismissRequest = { showFilterDialog = false },
            title = { Text("Filter Episodes") },
            text = {
                Column {
                    Text("Episode", style = MaterialTheme.typography.titleMedium)
                    listOf("S01E01", "S02E01", "S03E01").forEach { ep ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = tempEpisode == ep,
                                onClick = { tempEpisode = ep }
                            )
                            Text(ep)
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.updateFilters(
                        name = tempName.takeIf { it.isNotBlank() },
                        episode = tempEpisode
                    )
                    showFilterDialog = false
                    episodes.refresh()
                }) {
                    Text("Apply")
                }
            },
            dismissButton = {
                Button(onClick = { showFilterDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun EpisodeItem(
    episode: EpisodesResponseDto.Episodes,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = episode.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    text = "Episode: ${episode.episode}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Air Date: ${episode.airDate}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Characters: ${episode.characters.size}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}