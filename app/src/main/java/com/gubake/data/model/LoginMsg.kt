package com.gubake.data.model


import com.google.gson.annotations.SerializedName

data class LoginMsg(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("errors")
    val errors: String,
    @SerializedName("success")
    val success: Boolean
) {
    data class Data(
        @SerializedName("email")
        val email: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("token")
        val token: String,
        @SerializedName("username")
        val username: String
    )
}