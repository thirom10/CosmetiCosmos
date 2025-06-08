package com.example.cosmeticosmos.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.cosmeticosmos.ui.components.models.TabModel
import com.example.cosmeticosmos.ui.screens.*

// Componente Tabs y Output
@Composable
fun Tabs(tabItems: List<TabModel>) {
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                tabItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        icon = { Icon(item.icon, contentDescription = item.name) },
                        label = { Text(item.name) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            tabItems[selectedIndex].route()
        }
    }
}

val tabsExample = listOf(
    TabModel("Inicio", Icons.Default.Home) { HomeScreen() },
    TabModel("Productos", Icons.Default.ShoppingCart) { ProductScreen() },
    TabModel("Estadísticas", Icons.Default.BarChart) { SalesScreen() }
)


@Preview(showBackground = true)
@Composable
fun ScreenPreview () {
    Tabs(tabsExample)
}


