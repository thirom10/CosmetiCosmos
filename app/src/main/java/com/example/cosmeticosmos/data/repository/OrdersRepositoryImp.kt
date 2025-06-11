package com.example.cosmeticosmos.data.repository

import com.example.cosmeticosmos.data.model.OrdersDto
import com.example.cosmeticosmos.data.service.OrdersService
import com.example.cosmeticosmos.domain.model.*
import com.example.cosmeticosmos.domain.repository.OrdersRepository
import com.google.firebase.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrdersRepositoryImp @Inject constructor(
    private val service: OrdersService
) : OrdersRepository {

    override fun getOrders(): Flow<List<Order>> = flow {
        emit(service.getOrders().map { it.toDomain() })
    }.flowOn(Dispatchers.IO)

    override suspend fun createOrder(order: Order): String = 
        withContext(Dispatchers.IO) {
            service.createOrder(order.toDto())
        }

    override suspend fun updateOrder(order: Order) = 
        withContext(Dispatchers.IO) {
            service.updateOrder(order.toDto())
        }

    override suspend fun deleteOrder(orderId: String) = 
        withContext(Dispatchers.IO) {
            service.deleteOrder(orderId)
        }

    private fun Order.toDto(): OrdersDto =
        OrdersDto(
            id = id,
            client = client.toDataClient(),
            products = products.map { it.toDataProduct() },
            state = state,
            order_date = order_date
        )

    private fun OrdersDto.toDomain(): Order =
        Order(
            id = id,
            client = Client(
                name = client.name,
                tel = client.tel
            ),
            products = products.map { 
                OrderProduct(
                    amount = it.amount,
                    name = it.name,
                    price_unit = it.price_unit
                )
            },
            state = state,
            order_date = Timestamp.now()
        )
}
