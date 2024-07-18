package com.trendyol.trainingproductapi.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/authentication")
@Validated
class AuthenticationController
{
    @GetMapping("/basic")
    fun getBasicAuthToken(): ResponseEntity<Map<String, String>> {
        val auth = SecurityContextHolder.getContext().authentication
        val username = auth.name
        return ResponseEntity.ok(mapOf("username" to username))
    }


    @GetMapping("/token")
    fun getBearerToken(): ResponseEntity<Map<String, String>> {
        val auth = SecurityContextHolder.getContext().authentication
        val token = auth.credentials as String
        return ResponseEntity.ok(mapOf("token" to token))
    }
}