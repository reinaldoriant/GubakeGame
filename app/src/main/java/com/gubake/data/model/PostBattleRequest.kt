package com.gubake.data.model


import com.google.gson.annotations.SerializedName

data class PostBattleRequest(
    @SerializedName("mode")
    val mode: String,
    @SerializedName("result")
    val result: String
)