package com.trendyol.trainingproductapi.model.entity

data class Review(
    val id: Long,
    val productId: Long,
    val rating: Int,
    val comment: String
)