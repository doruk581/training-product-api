package com.trendyol.trainingproductapi.model.response

import com.trendyol.trainingproductapi.model.entity.Product

data class ProductResponse(
    val id: Long,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val stock: Int
) {

    companion object {
        fun fromProduct(product: Product) : ProductResponse {
            return ProductResponse(product.id,
                product.name,
                product.description,
                product.price,
                product.category,
                product.stock)
        }
    }
}