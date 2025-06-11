package com.example.cosmeticosmos.domain.usecase

import com.example.cosmeticosmos.domain.repository.OrdersRepository
import javax.inject.Inject

class DeleteOrderCase @Inject constructor(
    private val repository: OrdersRepository
) {
    suspend operator fun invoke(orderId: String) = repository.deleteOrder(orderId)
}
