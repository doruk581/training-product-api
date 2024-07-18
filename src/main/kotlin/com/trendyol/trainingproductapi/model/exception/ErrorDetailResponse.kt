package com.trendyol.trainingproductapi.model.exception

import org.apache.commons.lang3.builder.ToStringBuilder

class ErrorDetailResponse(
    var key: String = "",
    var message: String = "",
    var args: List<String> = listOf()
) {
    override fun toString() = ToStringBuilder.reflectionToString(this)!!
}
