package com.example.cosmeticosmos.data.repository

import com.example.cosmeticosmos.data.model.ProductDto
import com.example.cosmeticosmos.data.service.ProductService
import com.example.cosmeticosmos.domain.model.Product
import com.example.cosmeticosmos.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImp @Inject constructor(
    private val firebaseService: ProductService
) : ProductRepository {

    override suspend fun createProduct(product: Product): String {
        return firebaseService.createProduct(product.toDto())
    }

    override suspend fun getProducts(): List<Product> {
        return firebaseService.getProducts().map { productDto -> productDto.toDomain() }
    }

    override suspend fun getProductById(id: String): Product? {
        return firebaseService.getProductById(id)?.toDomain()
    }

    override suspend fun updateProduct(product: Product) {
        firebaseService.updateProduct(product.id, product.toDto())
    }

    override suspend fun deleteProduct(id: String) {
        firebaseService.deleteProduct(id)
    }

    private fun Product.toDto(): ProductDto =
        ProductDto(id = id, name = name, category = category, price = price, stock = stock)

    private fun ProductDto.toDomain(): Product =
        Product(id = id, name = name, category = category, price = price, stock = stock)
}