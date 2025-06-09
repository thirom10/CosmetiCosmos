package com.example.cosmeticosmos.di

import com.example.cosmeticosmos.data.repository.ProductRepositoryImp
import com.example.cosmeticosmos.data.service.ProductService
import com.example.cosmeticosmos.domain.repository.ProductRepository
import com.example.cosmeticosmos.domain.usecase.GetProductsCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Las dependencias vivirán mientras la app esté en memoria
object AppModule {

    // Provee ProductService (singleton)
    @Provides
    @Singleton
    fun provideProductService(): ProductService {
        return ProductService()
    }

    // Provee ProductRepository (inyectando ProductService)
    @Provides
    fun provideProductRepository(service: ProductService): ProductRepository {
        return ProductRepositoryImp(service)
    }

    // Provee casos de uso (inyectando ProductRepository)
    @Provides
    fun provideGetProductsCase(repository: ProductRepository): GetProductsCase {
        return GetProductsCase(repository)
    }
}