package com.example.cosmeticosmos.domain.usecase

import com.example.cosmeticosmos.domain.model.Order
import com.example.cosmeticosmos.domain.repository.OrdersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrdersCase @Inject constructor(
    private val repository: OrdersRepository
) {
    operator fun invoke(): Flow<List<Order>> = repository.getOrders()
}
