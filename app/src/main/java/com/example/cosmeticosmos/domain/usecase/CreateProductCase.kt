package com.example.cosmeticosmos.domain.usecase

import com.example.cosmeticosmos.domain.model.Product
import com.example.cosmeticosmos.domain.repository.ProductRepository
import javax.inject.Inject

class CreateProductCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(product: Product) {
        repository.createProduct(product)
    }
}