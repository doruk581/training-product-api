package com.trendyol.trainingproductapi.confguration

import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@Component
class RequestInterceptor : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val correlationId = request.getHeader("X-Correlation-Id") ?: generateCorrelationId()
        val agentName = request.getHeader("X-Agent-Name") ?: "Unknown"

        // Request attribute olarak ekleyelim
        request.setAttribute("correlationId", correlationId)
        request.setAttribute("agentName", agentName)

        // Response header olarak ekleyelim
        response.setHeader("X-Correlation-Id", correlationId)
        response.setHeader("X-Agent-Name", agentName)

        return true
    }

    private fun generateCorrelationId(): String {
        return java.util.UUID.randomUUID().toString()
    }
}