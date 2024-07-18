package com.trendyol.trainingproductapi.model.exception

class ReviewNotFoundException(private val reviewId: Long) : RuntimeException("Review not found for given id: $reviewId")