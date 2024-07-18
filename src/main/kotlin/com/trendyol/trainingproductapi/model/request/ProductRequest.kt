package com.trendyol.trainingproductapi.model.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class ProductRequest(
    @field:NotBlank(message = "Name is mandatory")
    @field:Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    val name: String,

    @field:NotBlank(message = "Description is mandatory")
    @field:Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    val description: String,

    @field:NotNull(message = "Price is mandatory")
    @field:Min(value = 0, message = "Price must be greater than or equal to 0")
    val price: Double,

    @field:NotBlank(message = "Category is mandatory")
    @field:Size(min = 2, max = 50, message = "Category must be between 2 and 50 characters")
    val category: String,

    @field:NotNull(message = "Stock is mandatory")
    @field:Min(value = 0, message = "Stock must be greater than or equal to 0")
    val stock: Int
)