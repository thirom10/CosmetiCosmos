package com.example.cosmeticosmos.ui.screens

import android.R.attr.onClick
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cosmeticosmos.data.model.OrdersDto
import com.example.cosmeticosmos.domain.model.Order
import androidx.compose.material3.FloatingActionButton
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cosmeticosmos.ui.components.ModalOrders
import com.example.cosmeticosmos.ui.viewmodel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesScreen(
    viewModel: OrderViewModel = hiltViewModel()
) {
    val orders by viewModel.ordersState.collectAsState()
    val error by viewModel.errorState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    var showAddOrderDialog by remember { mutableStateOf(false) }
    var selectedOrder by remember { mutableStateOf<Order?>(null) }
    
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Órdenes") },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddOrderDialog = true },
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Orden")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Text(
                    text = "Órdenes",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                if (error != null) {
                    Text(
                        text = error!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(orders) { order ->
                        OrderCard(order, onEditClick = { selectedOrder = it })
                    }
                }
            }
        }
    }

    if (showAddOrderDialog) {
        ModalOrders(
            title = "Agregar Orden",
            onDismiss = {
                showAddOrderDialog = false
                keyboardController?.hide()
            },
            onSave = { order ->
                viewModel.createOrder(order)
                showAddOrderDialog = false
                keyboardController?.hide()
            }
        )
    }

    selectedOrder?.let { order ->
        ModalOrders(
            title = "Editar Orden", 
            order = order,
            onDismiss = {
                selectedOrder = null
                keyboardController?.hide()
            },
            onSave = { updatedOrder ->
                viewModel.updateOrder(updatedOrder)
                selectedOrder = null
                keyboardController?.hide()
            }
        )
    }
}

@Composable
fun OrderCard(
    order: Order,
    onEditClick: (Order) -> Unit = {},
    viewModel: OrderViewModel = hiltViewModel()
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Cliente: ${order.client.name}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Teléfono: ${order.client.tel}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Estado: ${order.state}",
                style = MaterialTheme.typography.bodyMedium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Productos:",
                style = MaterialTheme.typography.titleSmall
            )
            order.products.forEach { product ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${product.name} (${product.amount})")
                    Text("$${product.price_unit * product.amount}")
                }
            }
            
            Text(
                text = "Total: $${order.products.sumOf { it.price_unit * it.amount }}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { viewModel.deleteOrder(order.id) },
                ) {
                    Icon(Icons.Default.DeleteOutline, contentDescription = "Borrar Orden")
                    Text("Eliminar")
                }
                
                Button(
                    onClick = { onEditClick(order) },
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar Orden")
                    Text("Editar")
                }
            }
        }
    }
}
