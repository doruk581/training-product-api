package com.trendyol.trainingproductapi.model.exception


class ProductNotFoundException(private val productId: Long) : RuntimeException("Product not found for given id: $productId")