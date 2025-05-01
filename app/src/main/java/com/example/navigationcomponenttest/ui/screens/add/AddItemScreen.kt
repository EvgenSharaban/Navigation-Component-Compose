package com.example.navigationcomponenttest.ui.screens.add

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
import com.example.navigationcomponenttest.ui.screens.add.AddItemViewModel.ScreenState

@Composable
fun AddItemScreen() {
    val viewModel: AddItemViewModel = hiltViewModel()

    AppScaffold(
        titleResId = R.string.add_item_screen,
        showNavigationUp = true
    ) { paddingValue ->
        ActionScreen(
            delegate = viewModel,
            content = { (screenState, onExecuteAction) ->
                AddItemContent(
                    screenState,
                    onExecuteAction,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValue)
                )
            }
        )
    }
}

@Composable
fun AddItemContent(
    screenState: ScreenState,
    onAddButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ItemDetails(
        state = ItemDetailsState(
            loadedItem = "",
            textFieldPlaceholder = stringResource(R.string.enter_new_item),
            actionButtonText = stringResource(R.string.add),
            isActionInProgress = screenState.isProgressVisible
        ),
        onActionButtonClicked = onAddButtonClicked,
        modifier = modifier
    )
}