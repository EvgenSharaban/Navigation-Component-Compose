package com.example.navigationcomponenttest.ui.screens.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.navigationcomponenttest.R
import com.example.navigationcomponenttest.ui.scaffold.AppScaffold

@Composable
fun ProfileScreen() {
    AppScaffold(
        titleResId = R.string.profile_screen,
        showNavigationUp = false
    ) { paddingValues ->
        Text(
            text = "Profile Screen",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .padding(paddingValues),
        )
    }
}