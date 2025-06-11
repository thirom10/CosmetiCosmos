package com.example.cosmeticosmos.domain.repository

import com.example.cosmeticosmos.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {
    fun getOrders(): Flow<List<Order>>
    suspend fun createOrder(order: Order): String
    suspend fun updateOrder(order: Order)
    suspend fun deleteOrder(orderId: String)
}
