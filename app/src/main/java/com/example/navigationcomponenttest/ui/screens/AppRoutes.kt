package com.example.navigationcomponenttest.ui.screens

import androidx.navigation.NavBackStackEntry
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Serializable
data object ItemsRoute

@Serializable
data object AddItemRoute

@Serializable
data class EditItemRout(
    val index: Int,
)

// ---------------

// this?.destination?.route convert to String, this function convert String to class
fun NavBackStackEntry?.routClass(): KClass<*>? {
    return this?.destination?.route
        ?.split("/")
        ?.first()
        ?.let { Class.forName(it) }
        ?.kotlin
}