package com.example.navigationcomponenttest.ui.screens.items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.navigationcomponenttest.R
import com.example.navigationcomponenttest.ui.scaffold.AppFloatingActionButton
import com.example.navigationcomponenttest.ui.scaffold.AppScaffold
import com.example.navigationcomponenttest.ui.screens.AddItemRoute
import com.example.navigationcomponenttest.ui.screens.LocalNavController
import com.example.navigationcomponenttest.ui.screens.items.ItemsViewModel.ScreenState

@Composable
fun ItemsScreen() {
    val viewModel: ItemsViewModel = hiltViewModel()
    val screenState = viewModel.stateFlow.collectAsState()

    AppScaffold(
        titleResId = R.string.items_screen,
        showNavigationUp = false,
        fabContent = {
            val navController = LocalNavController.current
            AppFloatingActionButton(
                onClick = { navController.navigate(AddItemRoute) }
            )
        }
    ) { paddingValue ->
        ItemsContent(
            getScreenState = { screenState.value },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        )
    }
}

@Composable
fun ItemsContent(
    getScreenState: () -> ScreenState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when (val screenState = getScreenState()) {
            ScreenState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is ScreenState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(screenState.items) {
                        Text(
                            text = it,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemScreenPreview() {
    ItemsContent(
        getScreenState = { ScreenState.Loading },
    )
}