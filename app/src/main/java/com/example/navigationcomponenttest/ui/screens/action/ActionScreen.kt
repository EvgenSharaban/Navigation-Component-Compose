package com.example.navigationcomponenttest.ui.screens.action

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.navigationcomponenttest.ui.components.ExceptionToMessageMapper
import com.example.navigationcomponenttest.ui.components.LoadResultContent
import com.example.navigationcomponenttest.ui.screens.LocalNavController

data class ActionContentState<State, Action>(
    val state: State,
    val onExecuteAction: (Action) -> Unit
)

@Composable
fun <State, Action> ActionScreen(
    delegate: ActionViewModel.Delegate<State, Action>,
    content: @Composable (ActionContentState<State, Action>) -> Unit,
    modifier: Modifier = Modifier,
    exceptionToMessageMapper: ExceptionToMessageMapper = ExceptionToMessageMapper.DEFAULT
) {
    val viewModel = viewModel<ActionViewModel<State, Action>> {
        ActionViewModel(delegate)
    }
    val navController = LocalNavController.current
    val screenState by viewModel.screenStateFlow.collectAsStateWithLifecycle(
        minActiveState = Lifecycle.State.RESUMED
    )
    LaunchedEffect(screenState) {
        screenState.exit?.let {
            navController.popBackStack()
            viewModel.onExitHandled()
        }
    }
    val context = LocalContext.current
    LaunchedEffect(screenState) {
        screenState.error?.let { exception ->
            val message = exceptionToMessageMapper.getUserMessage(exception, context)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            viewModel.onErrorHandled()
        }
    }
    val loadResult by viewModel.stateFlow.collectAsState()
    LoadResultContent(
        loadResult = loadResult,
        content = { state ->
            val actionContentState = ActionContentState(
                state = state,
                onExecuteAction = viewModel::execute
            )
            content(actionContentState)
        },
        modifier = modifier,
        onTryAgainAction = viewModel::load,
    )
}