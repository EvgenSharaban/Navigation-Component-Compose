package com.example.navigationcomponenttest.ui.screens.edit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.navigationcomponenttest.R
import com.example.navigationcomponenttest.ui.components.ItemDetails
import com.example.navigationcomponenttest.ui.components.ItemDetailsState
import com.example.navigationcomponenttest.ui.scaffold.AppScaffold
import com.example.navigationcomponenttest.ui.screens.action.ActionScreen
import com.example.navigationcomponenttest.ui.screens.edit.EditItemViewModel.ScreenState

@Composable
fun EditItemScreen(index: Int) {
    val viewModel = hiltViewModel<EditItemViewModel, EditItemViewModel.Factory> { factory ->
        factory.create(index)
    }
    AppScaffold(
        titleResId = R.string.edit_item_title,
        showNavigationUp = true,
    ) { paddingValue ->
        ActionScreen(
            delegate = viewModel,
            content = { (screenState, onExecuteAction) ->
                EditItemContent(
                    screenState,
                    onExecuteAction,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValue)
                )
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        )
    }
}

@Composable
private fun EditItemContent(
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