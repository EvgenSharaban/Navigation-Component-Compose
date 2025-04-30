package com.example.navigationcomponenttest.ui.screens.add

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.navigationcomponenttest.R
import com.example.navigationcomponenttest.ui.EventConsumer
import com.example.navigationcomponenttest.ui.components.ItemDetails
import com.example.navigationcomponenttest.ui.components.ItemDetailsState
import com.example.navigationcomponenttest.ui.scaffold.AppScaffold
import com.example.navigationcomponenttest.ui.screens.AddItemRoute
import com.example.navigationcomponenttest.ui.screens.LocalNavController
import com.example.navigationcomponenttest.ui.screens.add.AddItemViewModel.ScreenState
import com.example.navigationcomponenttest.ui.screens.routClass

@Composable
fun AddItemScreen() {
    val viewModel: AddItemViewModel = hiltViewModel()
    val screenState by viewModel.stateFlow.collectAsState()

    val navController = LocalNavController.current

    AppScaffold(
        titleResId = R.string.add_item_screen,
        showNavigationUp = true
    ) { paddingValue ->
        AddItemContent(
            screenState = screenState,
            onAddButtonClicked = viewModel::add,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        )
    }

    EventConsumer(viewModel.exitChannel) {
        if (navController.currentBackStackEntry.routClass() == AddItemRoute::class) {
            navController.popBackStack()
        }
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