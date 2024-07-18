package com.trendyol.trainingproductapi.service

import com.trendyol.trainingproductapi.model.exception.ReviewNotFoundException
import com.trendyol.trainingproductapi.model.entity.Review
import com.trendyol.trainingproductapi.model.request.ReviewRequest
import com.trendyol.trainingproductapi.repository.ReviewRepository
import org.springframework.stereotype.Service

@Service
class ReviewService(private val reviewRepository: ReviewRepository) {

    fun getAllReviews(): List<Review> = reviewRepository.findAll()

    fun getReviewById(id: Long): Review = reviewRepository.findById(id) ?: throw ReviewNotFoundException(id)

    fun createReview(request: ReviewRequest): Review {
        val review = Review(
            id = 0L,
            productId = request.productId,
            rating = request.rating,
            comment = request.comment
        )
        return reviewRepository.save(review)
    }

    fun updateReview(id: Long, request: ReviewRequest): Review {
        val existingReview = getReviewById(id)
        val updatedReview = existingReview.copy(
            productId = request.productId,
            rating = request.rating,
            comment = request.comment
        )
        return reviewRepository.save(updatedReview)
    }

    fun patchReview(id: Long, updates: Map<String, Any>): Review {
        val review = getReviewById(id)
        val updatedReview = review.copy(
            productId = updates["productId"] as Long? ?: review.productId,
            rating = updates["rating"] as Int? ?: review.rating,
            comment = updates["comment"] as String? ?: review.comment
        )
        return reviewRepository.save(updatedReview)
    }

    fun deleteReview(id: Long) {
        val review = getReviewById(id)
        reviewRepository.delete(review)
    }

    fun getReviewsByProductId(productId: Long): List<Review> = reviewRepository.findByProductId(productId)

    // Pagination, Filtering, Sorting
    fun getAllPagedFilteredSorted(
        page: Int,
        size: Int,
        productId: Long?,
        sortField: String?,
        sortOrder: String?
    ): List<Review> = reviewRepository.findAllPagedFilteredSorted(page, size, productId, sortField, sortOrder)
}

