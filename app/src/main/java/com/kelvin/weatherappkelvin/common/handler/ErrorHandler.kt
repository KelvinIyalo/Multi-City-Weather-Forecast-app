package com.kelvin.weatherappkelvin.common.handler

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody

fun handleApiHttpException(e: ResponseBody): ApiErrorResponses? {
    return try {

        val error = e.source()
        error.let {
            val moshiAdapter = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
                .adapter(ApiErrorResponses::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (t: Throwable) {
        ApiErrorResponses(error = ErrorHandler(message = "An unexpected error occurred"))
    }
}
