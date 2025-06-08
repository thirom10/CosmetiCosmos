package com.example.cosmeticosmos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cosmeticosmos.ui.components.models.TabModel
import com.example.cosmeticosmos.ui.screens.*

// Componente Tabs y Output
@Composable
fun Tabs(tabItems: List<TabModel>) {
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            Surface(
                tonalElevation = 4.dp,
                shadowElevation = 8.dp,
                color = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    tabItems.forEachIndexed { index, item ->
                        val selected = selectedIndex == index
                        val background = if (selected) Color(0xFFF3E8FF) else Color.Transparent
                        val contentColor = if (selected) Color(0xFF9B4DFF) else Color(0xFF4B4B4B)

                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50.dp))
                                .background(background)
                                .clickable { selectedIndex = index }
                                .padding(horizontal = 18.dp, vertical = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(modifier = Modifier.size(20.dp),
                                imageVector = item.icon, contentDescription = item.name, tint = contentColor)

                        }

                    }
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



val TabItems = listOf(
    TabModel(
        name = "Inicio",
        icon = Icons.Filled.Home,
        route = { HomeScreen() }
    ),
    TabModel(
        name = "Productos",
        icon = Icons.Filled.Inventory, // o Icons.Filled.Category si querés
        route = { ProductScreen() }
    ),
    TabModel(
        name = "Ventas",
        icon = Icons.Filled.ShoppingCart,
        route = { SalesScreen() }
    ),
    TabModel(
        name = "Metas",
        icon = Icons.Filled.Flag,
        route = { GoalsScreen() }
    ),
    TabModel(
        name = "Análisis",
        icon = Icons.Filled.BarChart,
        route = { AnalyticsScreen() }
    )
)



@Preview(showBackground = true)
@Composable
fun PrincipalScreen () {
    Tabs(TabItems)
}


