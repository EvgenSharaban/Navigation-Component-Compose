package com.example.navigationcomponenttest.ui.screens.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navigationcomponenttest.R
import com.example.navigationcomponenttest.ui.screens.LocalNavController
import kotlinx.coroutines.launch

@Composable
fun ItemsScreen() {
    val navController = LocalNavController.current
    ItemsContent(
//        onLaunchAddItemScreen = {
//            navController.navigate(AddItemRoute)
//        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsContent(
//    onLaunchAddItemScreen: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }

    if (isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { isSheetOpen = false },
            sheetState = sheetState,
            dragHandle = {
                // Кастомный drag handle с аватаром
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(80.dp) // Задаём ширину верхней черты
                            .height(4.dp)
                            .background(
                                color = Color.LightGray,
                                shape = RoundedCornerShape(2.dp)
                            )
                            .align(Alignment.Center)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.case_detail_sample),
                        contentDescription = "Avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterEnd)
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Тайтл элемента",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Здесь можно добавить описание элемента или другую полезную информацию.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                            isSheetOpen = false
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Закрыть")
                }
                Spacer(Modifier.height(56.dp))
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Items screen",
            modifier = Modifier.align(Alignment.Center),
            fontSize = 20.sp
        )
        FloatingActionButton(
//            onClick = onLaunchAddItemScreen,
            onClick = {
                isSheetOpen = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemScreenPreview() {
    ItemsContent(
//        onLaunchAddItemScreen = {}
    )
}