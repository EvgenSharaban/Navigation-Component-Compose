package com.example.navigationcomponenttest.ui.screens.edit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.navigationcomponenttest.R
import com.example.navigationcomponenttest.model.LoadResult
import com.example.navigationcomponenttest.ui.EventConsumer
import com.example.navigationcomponenttest.ui.components.ItemDetails
import com.example.navigationcomponenttest.ui.components.ItemDetailsState
import com.example.navigationcomponenttest.ui.components.LoadResultContent
import com.example.navigationcomponenttest.ui.scaffold.AppScaffold
import com.example.navigationcomponenttest.ui.screens.EditItemRout
import com.example.navigationcomponenttest.ui.screens.LocalNavController
import com.example.navigationcomponenttest.ui.screens.edit.EditItemViewModel.ScreenState
import com.example.navigationcomponenttest.ui.screens.routClass

@Composable
fun EditItemScreen(index: Int) {
    val viewModel = hiltViewModel<EditItemViewModel, EditItemViewModel.Factory> { factory ->
        factory.create(index)
    }
    val loadResult by viewModel.stateFlow.collectAsState()
    val navController = LocalNavController.current

    AppScaffold(
        titleResId = R.string.edit_item_title,
        showNavigationUp = true,
    ) { paddingValue ->
        EditItemContent(
            loadResult,
            onEditButtonClicked = viewModel::update,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        )
    }

    EventConsumer(channel = viewModel.exitChanel) {
        if (navController.currentBackStackEntry.routClass() == EditItemRout::class) {
            navController.popBackStack()
        }
    }
}

@Composable
fun EditItemContent(
    loadResult: LoadResult<ScreenState>,
    onEditButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LoadResultContent(
        loadResult = loadResult,
        content = { screenState ->
            SuccessEditItemContent(
                state = screenState,
                onEditButtonClicked = onEditButtonClicked,
                modifier = modifier
            )
        },
        modifier = modifier
    )
}

@Composable
private fun SuccessEditItemContent(
    state: ScreenState,
    onEditButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ItemDetails(
        state = ItemDetailsState(
            loadedItem = state.loadedItem,
            textFieldPlaceholder = stringResource(R.string.edit_item_title),
            actionButtonText = stringResource(R.string.edit),
            isActionInProgress = state.isEditInProgress
        ),
        onActionButtonClicked = onEditButtonClicked,
        modifier = modifier
    )
}