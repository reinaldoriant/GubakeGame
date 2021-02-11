package com.gubake.data.model


import com.google.gson.annotations.SerializedName

data class Users(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("success")
    val success: Boolean
) {
    data class Data(
        @SerializedName("email")
        val email: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("photo")
        var photo: String,
        @SerializedName("username")
        val username: String
    )
}