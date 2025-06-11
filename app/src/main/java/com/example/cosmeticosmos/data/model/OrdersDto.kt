package com.example.cosmeticosmos.data.model

data class OrdersDto(
    val id: String = "",
    val state: String = "",
    val client: ClientDto = ClientDto(),
    val products: List<ProductOrderDto> = emptyList(),
    val order_date: com.google.firebase.Timestamp = com.google.firebase.Timestamp.now(),
)

data class ClientDto(
    val name: String = "",
    val tel: String = ""
)

data class ProductOrderDto(
    val amount: Int = 0,
    val name: String = "",
    val price_unit: Double = 0.0
)
