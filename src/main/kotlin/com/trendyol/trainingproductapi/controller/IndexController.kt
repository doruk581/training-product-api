package com.trendyol.trainingproductapi.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.servlet.view.RedirectView


@Controller
class IndexController {

    @RequestMapping(value = ["/"], method = [RequestMethod.GET])
    fun redirectToSwagger(): RedirectView {
        return RedirectView("/swagger-ui.html")
    }
}
