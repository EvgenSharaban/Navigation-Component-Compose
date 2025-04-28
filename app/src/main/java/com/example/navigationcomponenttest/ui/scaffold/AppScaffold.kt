package com.example.navigationcomponenttest.ui.scaffold

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

@Composable
fun AppScaffold(
    @StringRes titleResId: Int,
    showNavigationUp: Boolean,
    fabContent: (@Composable () -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            AppToolbar(
                titleRes = titleResId,
                showNavigationUp = showNavigationUp,
            )
        },
        floatingActionButton = {
            fabContent?.invoke()
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}