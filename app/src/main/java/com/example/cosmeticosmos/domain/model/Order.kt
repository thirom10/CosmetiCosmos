package com.example.cosmeticosmos.domain.model

import com.example.cosmeticosmos.data.model.ClientDto
import com.example.cosmeticosmos.data.model.OrdersDto
import com.example.cosmeticosmos.data.model.ProductOrderDto
import com.google.firebase.Timestamp
import java.time.LocalDateTime

data class Order(
    val id: String = "",
    val client: Client = Client(),
    val products: List<OrderProduct> = emptyList(),
    val state: String = "Pendiente",
    val order_date: Timestamp
)

data class Client(
    val name: String = "",
    val tel: String = ""
)

data class OrderProduct(
    val amount: Int = 0,
    val name: String = "",
    val price_unit: Double = 0.0
)

fun Client.toDataClient(): ClientDto {
    return ClientDto(
        name = this.name,
        tel = this.tel
    )
}

fun OrderProduct.toDataProduct(): ProductOrderDto {
    return ProductOrderDto(
        amount = this.amount,
        name = this.name,
        price_unit = this.price_unit
    )
}

fun Order.toOrderDto(): OrdersDto {
    return OrdersDto(
        id = this.id,
        client = this.client.toDataClient(),
        products = this.products.map { it.toDataProduct() },
        state = this.state
    )
}

fun OrdersDto.toDomainOrder(): Order {
    return Order(
        id = this.id,
        client = Client(
            name = this.client.name,
            tel = this.client.tel
        ),
        products = this.products.map { 
            OrderProduct(
                amount = it.amount,
                name = it.name,
                price_unit = it.price_unit
            )
        },
        state = this.state,
        order_date = Timestamp.now()
    )
}
