package com.trendyol.trainingproductapi.controller

import com.trendyol.trainingproductapi.model.entity.Review
import com.trendyol.trainingproductapi.model.request.ReviewRequest
import com.trendyol.trainingproductapi.service.ReviewService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid
import org.springframework.http.HttpMethod

@RestController
@RequestMapping("/api/reviews")
@Validated
class ReviewController(private val reviewService: ReviewService) {

    @GetMapping
    @Operation(summary = "Get all reviews", description = "Retrieve a list of all reviews")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun getAllReviews(
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
        @RequestParam(required = false) productId: Long?,
        @RequestParam(required = false) sortField: String?,
        @RequestParam(required = false, defaultValue = "asc") sortOrder: String?
    ): ResponseEntity<List<Review>> {
        val reviews = reviewService.getAllPagedFilteredSorted(page, size, productId, sortField, sortOrder)
        return ResponseEntity.ok(reviews)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get review by ID", description = "Retrieve a review by its ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved review"),
        ApiResponse(responseCode = "404", description = "Review not found")
    ])
    fun getReviewById(@PathVariable id: Long): ResponseEntity<Review> {
        val review = reviewService.getReviewById(id)
        return ResponseEntity.ok(review)
    }

    @PostMapping
    @Operation(summary = "Create a new review", description = "Add a new review")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Successfully created review"),
        ApiResponse(responseCode = "400", description = "Invalid input"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun createReview(@Valid @RequestBody reviewRequest: ReviewRequest): ResponseEntity<Review> {
        val review = reviewService.createReview(reviewRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(review)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing review", description = "Update an existing review by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully updated review"),
        ApiResponse(responseCode = "400", description = "Invalid input"),
        ApiResponse(responseCode = "404", description = "Review not found"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun updateReview(@PathVariable id: Long, @Valid @RequestBody reviewRequest: ReviewRequest): ResponseEntity<Review> {
        val review = reviewService.updateReview(id, reviewRequest)
        return ResponseEntity.ok(review)
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update an existing review", description = "Partially update an existing review by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully patched review"),
        ApiResponse(responseCode = "400", description = "Invalid input"),
        ApiResponse(responseCode = "404", description = "Review not found"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun patchReview(@PathVariable id: Long, @RequestBody reviewRequest: Map<String, Any>): ResponseEntity<Review> {
        val review = reviewService.patchReview(id, reviewRequest)
        return ResponseEntity.ok(review)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a review", description = "Delete a review by its ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Successfully deleted review"),
        ApiResponse(responseCode = "404", description = "Review not found"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun deleteReview(@PathVariable id: Long): ResponseEntity<Void> {
        reviewService.deleteReview(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get reviews by product ID", description = "Retrieve a list of reviews by product ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved reviews"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun getReviewsByProductId(@PathVariable productId: Long): ResponseEntity<List<Review>> {
        val reviews = reviewService.getReviewsByProductId(productId)
        return ResponseEntity.ok(reviews)
    }

    @RequestMapping(method = [RequestMethod.OPTIONS], value = ["/{id}"])
    @Operation(summary = "Get allowed operations", description = "Retrieve allowed HTTP methods for the specified resource")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved allowed operations")
    ])
    fun optionsReview(@PathVariable id: Long): ResponseEntity<Void> {
        return ResponseEntity.ok().allow(
            HttpMethod.GET, HttpMethod.PUT, HttpMethod.PATCH, HttpMethod.DELETE, HttpMethod.OPTIONS
        ).build()
    }

    @RequestMapping(method = [RequestMethod.HEAD], value = ["/{id}"])
    @Operation(summary = "Get review metadata", description = "Retrieve metadata for the specified review")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved review metadata"),
        ApiResponse(responseCode = "404", description = "Review not found")
    ])
    fun headReview(@PathVariable id: Long): ResponseEntity<Void> {
        reviewService.getReviewById(id)  // Just to check if the review exists
        return ResponseEntity.ok().build()
    }
}
