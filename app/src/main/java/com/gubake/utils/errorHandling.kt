package com.gubake.utils

import retrofit2.HttpException
import org.json.JSONObject
import okhttp3.ResponseBody

fun Throwable.getServiceErrorMsg(): String {
    var messages=""
    when (this) {
        is HttpException -> {
            messages = this.response()?.errorBody().let {
                it?.let { it1 -> getErrorMessage(it1) }
            }.toString()
        }
        else -> message.toString()
    }
    return messages
}

private fun getErrorMessage(response: ResponseBody): String? {
    return try {
        val jsonObject = JSONObject(response.string())
        jsonObject.getString("errors")
    } catch (e: Exception) {
        e.message
    }
}