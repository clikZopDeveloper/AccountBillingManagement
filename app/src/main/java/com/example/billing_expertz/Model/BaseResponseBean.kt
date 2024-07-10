package com.example.billing_expertz.Model


import com.google.gson.annotations.SerializedName

data class BaseResponseBean(
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String
) {

}