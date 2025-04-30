package com.example.navigationcomponenttest.ui.screens.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.navigationcomponenttest.R
import com.example.navigationcomponenttest.model.LoadResult
import com.example.navigationcomponenttest.ui.components.LoadResultContent
import com.example.navigationcomponenttest.ui.scaffold.AppFloatingActionButton
import com.example.navigationcomponenttest.ui.scaffold.AppScaffold
import com.example.navigationcomponenttest.ui.screens.AddItemRoute
import com.example.navigationcomponenttest.ui.screens.EditItemRout
import com.example.navigationcomponenttest.ui.screens.LocalNavController
import com.example.navigationcomponenttest.ui.screens.items.ItemsViewModel.ScreenState

@Composable
fun ItemsScreen() {
    val viewModel: ItemsViewModel = hiltViewModel()
    val screenState = viewModel.stateFlow.collectAsState()
    val navController = LocalNavController.current

    AppScaffold(
        titleResId = R.string.items_screen,
        showNavigationUp = false,
        fabContent = {
            AppFloatingActionButton(
                onClick = { navController.navigate(AddItemRoute) }
            )
        }
    ) { paddingValue ->
        ItemsContent(
            getLoadResult = { screenState.value },
            onItemClicked = { index ->
                navController.navigate(EditItemRout(index))
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        )
    }
}

@Composable
fun ItemsContent(
    getLoadResult: () -> LoadResult<ScreenState>,
    onItemClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LoadResultContent(
        loadResult = getLoadResult(),
        content = { screenState ->
            LazyColumn(
                modifier = modifier,
            ) {
                itemsIndexed(screenState.items) { index, item ->
                    Text(
                        text = item,
                        modifier = Modifier
                            .clickable { onItemClicked(index) }
                            .fillMaxWidth()
                            .padding(12.dp)
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun ItemScreenPreview() {
    ItemsContent(
        getLoadResult = { LoadResult.Loading },
        onItemClicked = { },
    )
}