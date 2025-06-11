package com.example.cosmeticosmos.data.service

import com.example.cosmeticosmos.data.model.OrdersDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrdersService @Inject constructor() {
    private val firestore = FirebaseFirestore.getInstance()
    private val ordersCollection = firestore.collection("orders")

    suspend fun getOrders(): List<OrdersDto> {
        return ordersCollection.get().await().documents.mapNotNull { document ->
            document.toObject(OrdersDto::class.java)?.copy(id = document.id)
        }
    }

    suspend fun createOrder(order: OrdersDto): String {
        val document = ordersCollection.add(order).await()
        return document.id
    }

    suspend fun updateOrder(order: OrdersDto) {
        ordersCollection.document(order.id).set(order).await()
    }

    suspend fun deleteOrder(orderId: String) {
        ordersCollection.document(orderId).delete().await()
    }
}
