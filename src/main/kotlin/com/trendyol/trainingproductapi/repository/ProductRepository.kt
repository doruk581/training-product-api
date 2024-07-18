package com.trendyol.trainingproductapi.repository

import com.trendyol.trainingproductapi.model.entity.Product
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class ProductRepository {

    private val products = ConcurrentHashMap<Long, Product>()
    private var currentId = 1L

    fun findAll(): List<Product> = products.values.toList()

    fun findById(id: Long): Product? = products[id]

    fun save(product: Product): Product {
        val id = product.id.takeIf { it != 0L } ?: currentId++
        val newProduct = product.copy(id = id)
        products[id] = newProduct
        return newProduct
    }

    fun delete(product: Product) {
        products.remove(product.id)
    }

    fun findByCategory(category: String): List<Product> = products.values.filter { it.category == category }

    fun findByPriceBetween(minPrice: Double, maxPrice: Double): List<Product> = products.values.filter { it.price in minPrice..maxPrice }
}
