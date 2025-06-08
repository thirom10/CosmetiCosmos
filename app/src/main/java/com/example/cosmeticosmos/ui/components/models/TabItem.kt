package com.example.cosmeticosmos.ui.components.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class TabModel(
    val name: String,
    val icon: ImageVector,
    val route: @Composable () -> Unit
)