package com.example.cosmeticosmos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cosmeticosmos.domain.model.Product
import com.example.cosmeticosmos.domain.usecase.CreateProductCase
import com.example.cosmeticosmos.domain.usecase.GetProductsCase
import com.example.cosmeticosmos.domain.usecase.UpdateProductCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsCase: GetProductsCase,
    private val createProductCase: CreateProductCase,
    private val updateProductCase: UpdateProductCase
) : ViewModel() {

    private val _productsState = MutableStateFlow<List<Product>>(emptyList())
    val productsState: StateFlow<List<Product>> = _productsState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    fun loadProducts() {
        viewModelScope.launch {
            try {
                _productsState.value = getProductsCase()
                _errorState.value = null
            } catch (e: Exception) {
                _errorState.value = "Error al cargar los productos: ${e.message}"
            }
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            try {
                createProductCase(product)
                loadProducts() // Recargar la lista
                _errorState.value = null
            } catch (e: Exception) {
                _errorState.value = "Error al crear el producto: ${e.message}"
            }
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            try {
                updateProductCase(product)
                loadProducts() // Recargar la lista
                _errorState.value = null
            } catch (e: Exception) {
                _errorState.value = "Error al actualizar el producto: ${e.message}"
            }
        }
    }
}