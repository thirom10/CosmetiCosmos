package com.example.cosmeticosmos.data.service

import com.example.cosmeticosmos.data.model.ProductDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProductService {
        private val db = FirebaseFirestore.getInstance()
        private val productsCollection = db.collection("products")

        // CREATE
        suspend fun createProduct(product: ProductDto): String {
            val docRef = productsCollection.add(product).await()
            return docRef.id
        }

        // READ (Obtener todos)
        suspend fun getProducts(): List<ProductDto> {
            val querySnapshot = productsCollection.get().await()
            return querySnapshot.toObjects(ProductDto::class.java)
        }

        // READ (Obtener por ID)
        suspend fun getProductById(id: String): ProductDto? {
            return productsCollection.document(id).get().await().toObject(ProductDto::class.java)
        }

        // UPDATE
        suspend fun updateProduct(id: String, product: ProductDto) {
            productsCollection.document(id).set(product).await()
        }

        // DELETE
        suspend fun deleteProduct(id: String) {
            productsCollection.document(id).delete().await()
        }
}