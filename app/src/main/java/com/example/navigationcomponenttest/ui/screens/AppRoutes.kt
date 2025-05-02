package com.example.navigationcomponenttest.ui.screens

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Serializable
data object ItemsGraph {
    @Serializable
    data object ItemsRoute

    @Serializable
    data object AddItemRoute

    @Serializable
    data class EditItemRout(
        val index: Int,
    )
}

@Serializable
data object SettingsGraph {
    @Serializable
    data object SettingsRoute
}

@Serializable
data object ProfileGraph {
    @Serializable
    data object ProfileRoute
}


// ---------------

fun NavBackStackEntry?.routClass(): KClass<*>? {
    return this?.destination.routClass()
}

// this?.destination?.route convert to String, this function convert String to class
fun NavDestination?.routClass(): KClass<*>? {
    return this?.route
        ?.split("/")
        ?.first()
        ?.let { className ->
            generateSequence(className, ::replaceLastDotByDollar)
                .mapNotNull(::tryParseClass)
                .firstOrNull()
        }
}

private fun tryParseClass(className: String): KClass<*>? {
    return runCatching { Class.forName(className).kotlin }.getOrNull()
}

private fun replaceLastDotByDollar(input: String): String? {
    val index = input.lastIndexOf('.')
    return if (index != -1) {
        String(input.toCharArray().apply { set(index, '$') })
    } else {
        null
    }
}