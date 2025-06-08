package com.example.cosmeticosmos.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cosmeticosmos.ui.components.TabsNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen() {
    var showAddProductDialog by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    val products = listOf(
        Product(
            id = 1,
            name = "Labial Rosa Intenso",
            category = "Labiales",
            price = 25.0,
            stock = 15,
            needsRestock = false
        ),
        Product(
            id = 2,
            name = "Base Líquida Natural",
            category = "Bases",
            price = 45.0,
            stock = 3,
            needsRestock = true
        ),
        Product(
            id = 3,
            name = "Máscara de Pestañas",
            category = "Ojos",
            price = 30.0,
            stock = 8,
            needsRestock = false
        ),
        Product(
            id = 4,
            name = "Sombras Multicolor",
            category = "Ojos",
            price = 35.0,
            stock = 5,
            needsRestock = false
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Productos", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF7B1FA2)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddProductDialog = true },
                containerColor = Color(0xFF7B1FA2),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Product")
            }
        },
        containerColor = Color(0xFFF3E5F5)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                InventoryOverview(products)
            }
            item {
                StockAlerts(products)
            }
            item {
                Text(
                    text = "Tu Catálogo (${products.size})",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF7B1FA2),
                    modifier = Modifier.padding(start = 24.dp, top = 8.dp, bottom = 8.dp)
                )
            }
            items(products) { product ->
                ProductItem(
                    product = product,
                    onEditClick = { selectedProduct = product }
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }

    if (showAddProductDialog) {
        ProductFormDialog(
            title = "Agregar Producto",
            onDismiss = { showAddProductDialog = false },
            onSave = { /* Guardar producto */ }
        )
    }

    selectedProduct?.let { product ->
        ProductFormDialog(
            title = "Editar Producto",
            product = product,
            onDismiss = { selectedProduct = null },
            onSave = { /* Guardar producto */ }
        )
    }
}

@Composable
fun InventoryOverview(products: List<Product>) {
    val activeProducts = products.size
    val goodStockCount = products.count { it.stock > 5 }
    val needsRestockCount = products.count { it.stock <= 5 }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "¡Inventario organizado!",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Tienes $activeProducts productos bien gestionados",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Gestiona tu inventario",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "$activeProducts productos registrados",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Estado del Inventario",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(bottom = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InventoryStatusItem("Productos activos", "$activeProducts")
                InventoryStatusItem("Con stock bueno", "$goodStockCount")
                InventoryStatusItem("Necesitan stock", "$needsRestockCount")
            }

            Spacer(modifier = Modifier.height(8.dp))
            Divider()
        }
    }
}

@Composable
fun InventoryStatusItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun StockAlerts(products: List<Product>) {
    val productsNeedingRestock = products.filter { it.needsRestock }

    if (productsNeedingRestock.isNotEmpty()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFF3E0)
            ),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "¡Atención al Stock!",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Es momento de reabastecer estos productos",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp))


                Spacer(modifier = Modifier.height(8.dp))

                productsNeedingRestock.forEach { product ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "Solo ${product.stock}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductCatalog(products: List<Product>, onEditProduct: (Product) -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Tu Catálogo (${products.size})",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            // Optional: Add filter/sort controls here
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(products) { product ->
                ProductItem(
                    product = product,
                    onEditClick = { onEditProduct(product) }
                )
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onEditClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onEditClick() },
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = product.category,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp))


            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$${"%.2f".format(product.price)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (product.needsRestock) Color(0xFFFFEBEE) else Color(0xFFE8F5E9)
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Stock: ${product.stock}",
                        color = if (product.needsRestock) Color(0xFFD32F2F) else Color(0xFF2E7D32),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFormDialog(
    title: String,
    product: Product? = null,
    onDismiss: () -> Unit,
    onSave: (Product) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        modifier = Modifier.height(600.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp))


            var name by remember { mutableStateOf(product?.name ?: "") }
            var category by remember { mutableStateOf(product?.category ?: "") }
            var price by remember { mutableStateOf(product?.price?.toString() ?: "") }
            var stock by remember { mutableStateOf(product?.stock?.toString() ?: "") }

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre del producto") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp))


            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Categoría") },
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Select category")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Precio") },
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = stock,
                    onValueChange = { stock = it },
                    label = { Text("Stock") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val newProduct = Product(
                        id = product?.id ?: 0,
                        name = name,
                        category = category,
                        price = price.toDoubleOrNull() ?: 0.0,
                        stock = stock.toIntOrNull() ?: 0,
                        needsRestock = (stock.toIntOrNull() ?: 0) <= 5
                    )
                    onSave(newProduct)
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )
            ) {
                Text("Guardar")
            }
        }
    }
}

// Add this to your models package
data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val price: Double,
    val stock: Int,
    val needsRestock: Boolean
)

@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    ProductScreen()
}