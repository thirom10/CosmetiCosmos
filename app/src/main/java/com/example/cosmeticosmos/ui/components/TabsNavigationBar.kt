package com.example.cosmeticosmos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cosmeticosmos.ui.navigation.TabRoutes
import androidx.compose.foundation.layout.navigationBarsPadding

@Composable
fun TabsNavigationBar(
    currentRoute: String?,
    onTabSelected: (String) -> Unit
) {
    Surface(
        tonalElevation = 4.dp,
        shadowElevation = 8.dp,
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp)
                .navigationBarsPadding()
        ) {
            TabRoutes.allTabs.forEach { tab ->
                val selected = tab.route == currentRoute
                val background = if (selected) Color(0xFFF3E8FF) else Color.Transparent
                val contentColor = if (selected) Color(0xFF9B4DFF) else Color(0xFF4B4B4B)

                Column(
                    modifier = Modifier
                        .background(background)
                        .clickable { onTabSelected(tab.route) }
                        .padding(horizontal = 18.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(tab.icon, contentDescription = tab.name, tint = contentColor)
                }
            }
        }
    }
}
