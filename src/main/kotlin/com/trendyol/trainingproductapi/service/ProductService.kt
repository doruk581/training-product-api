package com.trendyol.trainingproductapi.service

import com.trendyol.trainingproductapi.model.entity.Product
import com.trendyol.trainingproductapi.model.request.ProductRequest
import com.trendyol.trainingproductapi.repository.ProductRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    @Cacheable("products")
    fun getAllProducts(): List<Product> = productRepository.findAll()

    fun getProductById(id: Long): Product = productRepository.findById(id) ?: throw RuntimeException("Product not found")

    fun createProduct(request: ProductRequest): Product {
        val product = Product(
            id = 0L,  // id sıfır olarak veriyoruz çünkü `save` metodu bu id'yi değiştirecek
            name = request.name,
            description = request.description,
            price = request.price,
            category = request.category,
            stock = request.stock
        )
        return productRepository.save(product)
    }

    fun updateProduct(id: Long, request: ProductRequest): Product {
        val existingProduct = getProductById(id)
        val updatedProduct = existingProduct.copy(
            name = request.name,
            description = request.description,
            price = request.price,
            category = request.category,
            stock = request.stock
        )
        return productRepository.save(updatedProduct)
    }

    fun patchProduct(id: Long, updates: Map<String, Any>): Product {
        val product = getProductById(id)
        val updatedProduct = product.copy(
            name = updates["name"] as String? ?: product.name,
            description = updates["description"] as String? ?: product.description,
            price = updates["price"] as Double? ?: product.price,
            category = updates["category"] as String? ?: product.category,
            stock = updates["stock"] as Int? ?: product.stock
        )
        return productRepository.save(updatedProduct)
    }

    fun deleteProduct(id: Long) {
        val product = getProductById(id)
        productRepository.delete(product)
    }

    fun getProductsByCategory(category: String): List<Product> = productRepository.findByCategory(category)

    fun getProductsByPriceRange(minPrice: Double, maxPrice: Double): List<Product> = productRepository.findByPriceBetween(minPrice, maxPrice)

    fun updateProductStock(id: Long, stock: Int): Product {
        val product = getProductById(id)
        val updatedProduct = product.copy(stock = stock)
        return productRepository.save(updatedProduct)
    }
}

