package com.trendyol.trainingproductapi.controller

import com.trendyol.trainingproductapi.model.request.ProductRequest
import com.trendyol.trainingproductapi.model.response.ProductResponse
import com.trendyol.trainingproductapi.service.ProductService
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
@RequestMapping("/v1/products")
@Validated
class ProductController(private val productService: ProductService) {

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieve a list of all products")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun getAllProducts(): ResponseEntity<List<ProductResponse>> {
        val products = productService.getAllProducts()
        return ResponseEntity.ok(products.map { ProductResponse.fromProduct(it) })
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve a product by its ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved product"),
        ApiResponse(responseCode = "404", description = "Product not found")
    ])
    fun getProductById(@PathVariable id: Long): ResponseEntity<ProductResponse> {
        val product = productService.getProductById(id)
        return ResponseEntity.ok(ProductResponse.fromProduct(product))
    }

    @PostMapping
    @Operation(summary = "Create a new product", description = "Add a new product to the inventory")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Successfully created product"),
        ApiResponse(responseCode = "400", description = "Invalid input"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun createProduct(@Valid @RequestBody productRequest: ProductRequest): ResponseEntity<ProductResponse> {
        val product = productService.createProduct(productRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductResponse.fromProduct(product))
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product", description = "Update an existing product by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully updated product"),
        ApiResponse(responseCode = "400", description = "Invalid input"),
        ApiResponse(responseCode = "404", description = "Product not found"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun updateProduct(@PathVariable id: Long, @Valid @RequestBody productRequest: ProductRequest): ResponseEntity<ProductResponse> {
        val product = productService.updateProduct(id, productRequest)
        return ResponseEntity.ok(ProductResponse.fromProduct(product))
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update an existing product", description = "Partially update an existing product by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully patched product"),
        ApiResponse(responseCode = "400", description = "Invalid input"),
        ApiResponse(responseCode = "404", description = "Product not found"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun patchProduct(@PathVariable id: Long, @RequestBody productRequest: Map<String, Any>): ResponseEntity<ProductResponse> {
        val product = productService.patchProduct(id, productRequest)
        return ResponseEntity.ok(ProductResponse.fromProduct(product))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product", description = "Delete a product by its ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Successfully deleted product"),
        ApiResponse(responseCode = "404", description = "Product not found"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Void> {
        productService.deleteProduct(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get products by category", description = "Retrieve a list of products by category")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved products"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun getProductsByCategory(@PathVariable category: String): ResponseEntity<List<ProductResponse>> {
        val products = productService.getProductsByCategory(category)
        return ResponseEntity.ok(products.map { ProductResponse.fromProduct(it) })
    }

    @GetMapping("/price-range")
    @Operation(summary = "Get products by price range", description = "Retrieve a list of products within a specified price range")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved products"),
        ApiResponse(responseCode = "400", description = "Invalid price range"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun getProductsByPriceRange(@RequestParam minPrice: Double, @RequestParam maxPrice: Double): ResponseEntity<List<ProductResponse>> {
        val products = productService.getProductsByPriceRange(minPrice, maxPrice)
        return ResponseEntity.ok(products.map { ProductResponse.fromProduct(it) })
    }

    @PutMapping("/{id}/stock")
    @Operation(summary = "Update product stock", description = "Update the stock quantity of a product by its ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully updated product stock"),
        ApiResponse(responseCode = "400", description = "Invalid stock value"),
        ApiResponse(responseCode = "404", description = "Product not found"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    fun updateProductStock(@PathVariable id: Long, @RequestParam stock: Int): ResponseEntity<ProductResponse> {
        val product = productService.updateProductStock(id, stock)
        return ResponseEntity.ok(ProductResponse.fromProduct(product))
    }

    @RequestMapping(method = [RequestMethod.OPTIONS], value = ["/{id}"])
    @Operation(summary = "Get allowed operations", description = "Retrieve allowed HTTP methods for the specified resource")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved allowed operations")
    ])
    fun optionsProduct(@PathVariable id: Long): ResponseEntity<Void> {
        return ResponseEntity.ok().allow(
            HttpMethod.GET, HttpMethod.PUT, HttpMethod.PATCH, HttpMethod.DELETE, HttpMethod.OPTIONS
        ).build()
    }

    @RequestMapping(method = [RequestMethod.HEAD], value = ["/{id}"])
    @Operation(summary = "Get product metadata", description = "Retrieve metadata for the specified product")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Successfully retrieved product metadata"),
        ApiResponse(responseCode = "404", description = "Product not found")
    ])
    fun headProduct(@PathVariable id: Long): ResponseEntity<Void> {
        productService.getProductById(id)  // Just to check if the product exists
        return ResponseEntity.ok().build()
    }
}
