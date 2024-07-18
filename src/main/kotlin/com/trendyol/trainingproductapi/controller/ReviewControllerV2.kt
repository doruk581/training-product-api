package com.trendyol.trainingproductapi.controller

import com.trendyol.trainingproductapi.model.entity.Review
import com.trendyol.trainingproductapi.service.ReviewService
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v2/hateoas/reviews")
class ReviewHateoasControllerV2(private val reviewService: ReviewService) {

    @GetMapping("/{id}")
    fun getReviewById(@PathVariable id: Long): EntityModel<Review> {
        val review = reviewService.getReviewById(id)
        val resource = EntityModel.of(review)
        resource.add(linkTo(methodOn(this::class.java).getReviewById(id)).withSelfRel())
        resource.add(linkTo(methodOn(this::class.java).getAllReviews()).withRel("all-reviews"))
        return resource
    }

    @GetMapping
    fun getAllReviews(): List<Review> {
        return reviewService.getAllReviews()
    }
}
