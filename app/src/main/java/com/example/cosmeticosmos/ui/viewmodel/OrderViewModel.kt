package com.example.cosmeticosmos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cosmeticosmos.data.model.OrdersDto
import com.example.cosmeticosmos.domain.model.Order
import com.example.cosmeticosmos.domain.repository.OrdersRepository
import com.example.cosmeticosmos.domain.usecase.CreateOrderCase
import com.example.cosmeticosmos.domain.usecase.DeleteOrderCase
import com.example.cosmeticosmos.domain.usecase.GetOrdersCase
import com.example.cosmeticosmos.domain.usecase.UpdateOrderCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getOrdersCase: GetOrdersCase,
    private val createOrderCase: CreateOrderCase,
    private val updateOrderCase: UpdateOrderCase,
    private val deleteOrderCase: DeleteOrderCase
) : ViewModel() {

    private val _ordersState = MutableStateFlow<List<Order>>(emptyList())
    val ordersState: StateFlow<List<Order>> = _ordersState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadOrders()
    }

    fun loadOrders() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                getOrdersCase().collect { orders ->
                    _ordersState.value = orders
                    _errorState.value = null
                }
            } catch (e: Exception) {
                _errorState.value = "Error al cargar las órdenes: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createOrder(order: Order) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                createOrderCase(order)
                loadOrders()
                _errorState.value = null
            } catch (e: Exception) {
                _errorState.value = "Error al crear la orden: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateOrder(order: Order) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                updateOrderCase(order)
                loadOrders()
                _errorState.value = null
            } catch (e: Exception) {
                _errorState.value = "Error al crear la orden: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteOrder(orderId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                deleteOrderCase(orderId)
                loadOrders()
                _errorState.value = null
            } catch (e: Exception) {
                _errorState.value = "Error al eliminar la orden: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
