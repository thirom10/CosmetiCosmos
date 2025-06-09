package com.example.cosmeticosmos.data.model

import com.google.firebase.firestore.DocumentId

data class ProductDto(
    @DocumentId val id: String = "",
    val name: String = "",
    val category: String = "",
    val price: Double = 0.0,
    val stock: Int = 0
)
