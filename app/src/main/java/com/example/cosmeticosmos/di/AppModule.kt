package com.example.cosmeticosmos.di

import com.example.cosmeticosmos.data.repository.ProductRepositoryImp
import com.example.cosmeticosmos.data.service.ProductService
import com.example.cosmeticosmos.domain.repository.ProductRepository
import com.example.cosmeticosmos.domain.usecase.CreateProductCase
import com.example.cosmeticosmos.domain.usecase.GetProductsCase
import com.example.cosmeticosmos.domain.usecase.UpdateProductCase
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

    @Provides
    @Singleton
    fun provideCreateProductCase(repository: ProductRepository): CreateProductCase {
        return CreateProductCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateProductCase(repository: ProductRepository): UpdateProductCase {
        return UpdateProductCase(repository)
    }

}