package com.example.cosmeticosmos.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.cosmeticosmos.domain.model.*
import com.google.firebase.Timestamp

@Composable
fun ModalOrders(
    title: String,
    order: Order? = null,
    onDismiss: () -> Unit,
    onSave: (Order) -> Unit
) {
    // Inicializamos los estados con los valores de la orden si existe
    var clientName by remember { mutableStateOf(order?.client?.name ?: "") }
    var clientPhone by remember { mutableStateOf(order?.client?.tel ?: "") }
    var products by remember { mutableStateOf(order?.products ?: listOf()) }
    var state by remember { mutableStateOf(order?.state ?: "Pendiente") }
    
    var showAddProduct by remember { mutableStateOf(false) }
    var tempProductName by remember { mutableStateOf("") }
    var tempProductAmount by remember { mutableStateOf("") }
    var tempProductPrice by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                // Título y botón cerrar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, "Cerrar")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Información del cliente
                OutlinedTextField(
                    value = clientName,
                    onValueChange = { clientName = it },
                    label = { Text("Nombre del cliente") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = clientPhone,
                    onValueChange = { clientPhone = it },
                    label = { Text("Teléfono") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Lista de productos
                Text("Productos", style = MaterialTheme.typography.titleMedium)
                LazyColumn(
                    modifier = Modifier.height(200.dp)
                ) {
                    items(products) { product ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("${product.amount}x ${product.name}")
                                Text("$${product.price_unit}", style = MaterialTheme.typography.bodySmall)
                            }
                            IconButton(
                                onClick = {
                                    products = products.filter { it != product }
                                }
                            ) {
                                Icon(Icons.Default.Close, "Eliminar producto")
                            }
                        }
                    }
                }

                // Botón para agregar producto
                Button(
                    onClick = { showAddProduct = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Add, "Agregar producto")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Agregar Producto")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Estado de la orden
                OutlinedTextField(
                    value = state,
                    onValueChange = { state = it },
                    label = { Text("Estado") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Botón guardar actualizado para mantener el ID existente
                Button(
                    onClick = {
                        val updatedOrder = Order(
                            id = order?.id ?: "",  // Mantenemos el ID si existe
                            client = Client(
                                name = clientName,
                                tel = clientPhone
                            ),
                            products = products,
                            state = state,
                            order_date = order?.order_date ?: Timestamp.now(),  // Mantenemos la fecha original si existe
                        )
                        onSave(updatedOrder)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (order == null) "Crear Orden" else "Actualizar Orden")
                }
            }
        }
    }

    // Modal para agregar producto
    if (showAddProduct) {
        AlertDialog(
            onDismissRequest = { showAddProduct = false },
            title = { title },
            text = {
                Column {
                    OutlinedTextField(
                        value = tempProductName,
                        onValueChange = { tempProductName = it },
                        label = { Text("Nombre del producto") }
                    )
                    OutlinedTextField(
                        value = tempProductAmount,
                        onValueChange = { tempProductAmount = it },
                        label = { Text("Cantidad") }
                    )
                    OutlinedTextField(
                        value = tempProductPrice,
                        onValueChange = { tempProductPrice = it },
                        label = { Text("Precio unitario") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    val newProduct = OrderProduct(
                        amount = tempProductAmount.toIntOrNull() ?: 0,
                        name = tempProductName,
                        price_unit = tempProductPrice.toDoubleOrNull() ?: 0.0
                    )
                    products = products + newProduct
                    showAddProduct = false
                    tempProductName = ""
                    tempProductAmount = ""
                    tempProductPrice = ""
                }) {
                    Text("Agregar")
                }
            },
            dismissButton = {
                Button(onClick = { showAddProduct = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
