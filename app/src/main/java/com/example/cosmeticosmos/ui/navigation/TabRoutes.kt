package com.example.cosmeticosmos.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class TabRoutes(val route: String, val icon: ImageVector, val name: String) {
    object Home : TabRoutes("home", Icons.Filled.Home, "Inicio")
    object Products : TabRoutes("products", Icons.Filled.Inventory, "Productos")
    object Sales : TabRoutes("sales", Icons.Filled.ShoppingCart, "Ventas")
    object Goals : TabRoutes("goals", Icons.Filled.Flag, "Metas")
    object Analytics : TabRoutes("analytics", Icons.Filled.BarChart, "Análisis")

    companion object {
        val allTabs = listOf(Home, Products, Sales, Goals, Analytics)
    }
}
