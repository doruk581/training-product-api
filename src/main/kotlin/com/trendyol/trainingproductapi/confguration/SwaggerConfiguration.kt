package com.trendyol.trainingproductapi.confguration

import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenAPIConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Training Product API")
                    .description("Training Product API documentation")
                    .version("1.0.0")
                    .contact(Contact().name("Doruk Sü").email("doruk.su@trendyol.com"))
                    .license(License().name("Apache 2.0").url("http://springdoc.org"))
            )
            .externalDocs(
                ExternalDocumentation()
                    .description("E-commerce Wiki Documentation")
                    .url("https://trendyol.com")
            )
    }
}