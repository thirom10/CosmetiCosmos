package com.example.cosmeticosmos.domain.repository

import com.example.cosmeticosmos.domain.model.Product


interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getProductById(id: String): Product?
    suspend fun createProduct(product: Product): String
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(id: String)
}