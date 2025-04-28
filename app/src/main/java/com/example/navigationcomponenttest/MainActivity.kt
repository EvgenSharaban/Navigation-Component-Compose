package com.example.navigationcomponenttest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.navigationcomponenttest.model.ItemsRepository
import com.example.navigationcomponenttest.ui.screens.AddItemRoute
import com.example.navigationcomponenttest.ui.screens.AppToolbar
import com.example.navigationcomponenttest.ui.screens.ItemsRoute
import com.example.navigationcomponenttest.ui.screens.LocalNavController
import com.example.navigationcomponenttest.ui.screens.NavigateUpAction
import com.example.navigationcomponenttest.ui.screens.add.AddItemScreen
import com.example.navigationcomponenttest.ui.screens.items.ItemsScreen
import com.example.navigationcomponenttest.ui.theme.NavigationComponentTestTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var itemsRepository: ItemsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationComponentTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavApp()
                }
            }
        }
    }
}

@Composable
fun NavApp() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val titleRes = when (currentBackStackEntry?.destination?.route) {
        ItemsRoute -> R.string.items_screen
        AddItemRoute -> R.string.add_item_screen
        else -> R.string.app_name
    }
    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        Scaffold(
            topBar = {
                AppToolbar(
                    navigateUpAction = if (navController.previousBackStackEntry == null) {
                        NavigateUpAction.Hidden
                    } else {
                        NavigateUpAction.Visible(
                            onClick = {
                                navController.navigateUp()
                            }
                        )
                    },
                    titleRes = titleRes,
                )
            },
            floatingActionButton = {
                if (currentBackStackEntry?.destination?.route == ItemsRoute) {
                    FloatingActionButton(
                        onClick = { navController.navigate(AddItemRoute) }
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                }
            }
        ) { paddingValue ->
            NavHost(
                navController = navController,
                startDestination = ItemsRoute,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValue)
            ) {
                composable(ItemsRoute) { ItemsScreen() }
                composable(AddItemRoute) { AddItemScreen() }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun NavAppPreview() {
    NavigationComponentTestTheme {
        NavApp()
    }
}