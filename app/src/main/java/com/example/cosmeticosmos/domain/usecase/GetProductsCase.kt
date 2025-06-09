package com.example.cosmeticosmos.domain.usecase

import com.example.cosmeticosmos.domain.model.Product
import com.example.cosmeticosmos.domain.repository.ProductRepository

class GetProductsCase (
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(): List<Product> {
        return productRepository.getProducts()
    }
}