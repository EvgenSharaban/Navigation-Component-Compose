package com.example.navigationcomponenttest.ui.scaffold

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.navigationcomponenttest.ui.screens.AppNavigationBar
import com.example.navigationcomponenttest.ui.screens.LocalNavController
import com.example.navigationcomponenttest.ui.screens.MainTabs

@Composable
fun AppScaffold(
    @StringRes titleResId: Int,
    showNavigationUp: Boolean,
    fabContent: (@Composable () -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    val navController = LocalNavController.current
    Scaffold(
        topBar = {
            AppToolbar(
                titleRes = titleResId,
                showNavigationUp = showNavigationUp,
            )
        },
        floatingActionButton = {
            fabContent?.invoke()
        },
        bottomBar = {
            AppNavigationBar(navController = navController, tabs = MainTabs)
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}