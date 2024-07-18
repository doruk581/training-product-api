package com.trendyol.trainingproductapi.confguration


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
class BearerTokenSecurityConfig {

@Bean
fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http
        .authorizeHttpRequests { authz ->
            authz
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
        }
        .addFilterBefore(BearerTokenFilter(), BasicAuthenticationFilter::class.java)
        .csrf().disable()
    return http.build()
}
}