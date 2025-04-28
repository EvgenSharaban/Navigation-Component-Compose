package com.example.navigationcomponenttest.ui.screens.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.navigationcomponenttest.R
import com.example.navigationcomponenttest.ui.scaffold.AppScaffold
import com.example.navigationcomponenttest.ui.screens.AddItemRoute
import com.example.navigationcomponenttest.ui.screens.EventConsumer
import com.example.navigationcomponenttest.ui.screens.LocalNavController
import com.example.navigationcomponenttest.ui.screens.add.AddItemViewModel.ScreenState

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
        if (navController.currentBackStackEntry?.destination?.route == AddItemRoute) {
            navController.popBackStack()
        }
    }
}

@Composable
fun AddItemContent(
    screenState: ScreenState,
    onAddButtonClicked: (String) -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        var inputText by rememberSaveable { mutableStateOf("") }
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            placeholder = { Text(stringResource(R.string.enter_new_item)) },
            enabled = screenState.isTextInputEnabled,
        )
        Button(
            onClick = { onAddButtonClicked(inputText) },
            enabled = screenState.isAddButtonEnabled(inputText),
        ) {
            Text(stringResource(R.string.add))
        }
        Box(modifier = Modifier.size(32.dp)) {
            if (screenState.isProgressVisible) {
                CircularProgressIndicator(Modifier.fillMaxSize())
            }
        }
    }
}