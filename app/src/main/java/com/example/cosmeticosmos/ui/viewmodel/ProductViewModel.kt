package com.example.cosmeticosmos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cosmeticosmos.domain.model.Product
import com.example.cosmeticosmos.domain.usecase.GetProductsCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsCase: GetProductsCase
) : ViewModel() {

    private val _productsState = MutableStateFlow<List<Product>>(emptyList())
    val productsState: StateFlow<List<Product>> = _productsState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    fun loadProducts() {
        viewModelScope.launch {
            try {
                _productsState.value = getProductsCase()
            } catch (e: Exception) {
                _errorState.value = "Error al cargar los productos: ${e.message}"
            }
        }
    }
}
