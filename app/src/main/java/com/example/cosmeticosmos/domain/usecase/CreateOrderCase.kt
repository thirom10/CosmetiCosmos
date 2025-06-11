package com.example.cosmeticosmos.domain.usecase

import com.example.cosmeticosmos.domain.model.Order
import com.example.cosmeticosmos.domain.repository.OrdersRepository
import javax.inject.Inject

class CreateOrderCase @Inject constructor(
    private val repository: OrdersRepository
) {
    suspend operator fun invoke(order: Order): String = repository.createOrder(order)
}
