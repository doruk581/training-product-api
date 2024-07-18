package com.trendyol.trainingproductapi.repository

import com.trendyol.trainingproductapi.model.entity.Review
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class ReviewRepository {

    private val reviews = ConcurrentHashMap<Long, Review>()
    private var currentId = 1L

    fun findAll(): List<Review> = reviews.values.toList()

    fun findById(id: Long): Review? = reviews[id]

    fun save(review: Review): Review {
        val id = review.id.takeIf { it != 0L } ?: currentId++
        val newReview = review.copy(id = id)
        reviews[id] = newReview
        return newReview
    }

    fun delete(review: Review) {
        reviews.remove(review.id)
    }

    fun findByProductId(productId: Long): List<Review> = reviews.values.filter { it.productId == productId }

    // Pagination, Filtering, Sorting
    fun findAllPagedFilteredSorted(
        page: Int,
        size: Int,
        productId: Long?,
        sortField: String?,
        sortOrder: String?
    ): List<Review> {
        var filteredReviews = reviews.values.toList()

        // Filtering
        if (productId != null) {
            filteredReviews = filteredReviews.filter { it.productId == productId }
        }

        // Sorting
        if (sortField != null) {
            filteredReviews = when (sortOrder?.lowercase()) {
                "desc" -> filteredReviews.sortedWith(compareByDescending { it.getField<Comparable<Any>>(sortField) })
                else -> filteredReviews.sortedWith(compareBy { it.getField<Comparable<Any>>(sortField) })
            }
        }

        // Pagination
        val fromIndex = page * size
        val toIndex = kotlin.math.min(fromIndex + size, filteredReviews.size)
        return if (fromIndex > filteredReviews.size) emptyList() else filteredReviews.subList(fromIndex, toIndex)
    }

    private inline fun <reified T : Comparable<T>> Review.getField(fieldName: String): T? {
        return when (fieldName) {
            "rating" -> this.rating as? T
            "comment" -> this.comment as? T
            "productId" -> this.productId as? T
            else -> null
        }
    }
}


