package com.trendyol.trainingproductapi.model.exception

import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.builder.ToStringBuilder

class ErrorResponse {
    var exception: String = ""
    var errors = mutableListOf<ErrorDetailResponse>()
    var timestamp = System.currentTimeMillis()

    constructor()

    constructor(exception: String = "", vararg exceptionMessage: String?) {
        if (StringUtils.isNotBlank(exception)) this.exception = exception

        if (!exceptionMessage.isNullOrEmpty()) {
            exceptionMessage.forEach {
                errors.add(ErrorDetailResponse(message = it!!))
            }
        }
    }

    fun addError(errorDetailDTO: ErrorDetailResponse) {
        errors.add(errorDetailDTO)
    }

    override fun toString() = ToStringBuilder.reflectionToString(this)!!
}
