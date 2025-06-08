package com.example.cosmeticosmos.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import androidx.compose.material3.*
import com.example.cosmeticosmos.ui.components.TabsNavigationBar
import com.example.cosmeticosmos.ui.screens.*

@Preview(showBackground = true)
@Composable
fun MainView() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            TabsNavigationBar(currentRoute = currentRoute) { route ->
                navController.navigate(route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TabRoutes.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(TabRoutes.Home.route) { HomeScreen() }
            composable(TabRoutes.Products.route) { ProductScreen() }
            composable(TabRoutes.Sales.route) { SalesScreen() }
            composable(TabRoutes.Goals.route) { GoalsScreen() }
            composable(TabRoutes.Analytics.route) { AnalyticsScreen() }
        }
    }
}
