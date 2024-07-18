package com.trendyol.trainingproductapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class TrainingProductApiApplication

fun main(args: Array<String>) {
    runApplication<TrainingProductApiApplication>(*args)
}
