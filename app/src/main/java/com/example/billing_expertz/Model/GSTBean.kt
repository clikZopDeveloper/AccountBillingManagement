package com.example.billing_expertz.Model


import com.google.gson.annotations.SerializedName

data class GSTBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String // Data loaded successfully.
) {
    data class Data(
        @SerializedName("created_at")
        val createdAt: String, // 2024-05-01 18:13:51
        @SerializedName("gst")
        val gst: String, // 5.00
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("updated_at")
        val updatedAt: Any // null
    )
}