package com.trendyol.trainingproductapi.model.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class ReviewRequest(
    @field:NotNull(message = "Product ID is mandatory")
    val productId: Long,

    @field:NotNull(message = "Rating is mandatory")
    @field:Min(value = 1, message = "Rating must be at least 1")
    val rating: Int,

    @field:NotBlank(message = "Comment is mandatory")
    @field:Size(min = 5, max = 500, message = "Comment must be between 5 and 500 characters")
    val comment: String
)