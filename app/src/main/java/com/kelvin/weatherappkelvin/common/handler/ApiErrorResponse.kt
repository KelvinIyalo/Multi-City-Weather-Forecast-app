package com.kelvin.weatherappkelvin.common.handler

data class ApiErrorResponse(
    val message: String? = null,
    val validationMessages: List<String>? = null,
    val errorCode: Int? = null
)


//{"error":{"code":1006,"message":"No matching location found."}}
data class ApiErrorResponses(
    val error:ErrorHandler
)

data class ErrorHandler(
    val message: String? = null
)
