package com.example.billing_expertz.Model


import com.google.gson.annotations.SerializedName

data class CategoryBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String // Data loaded successfully.
) {
    data class Data(
        @SerializedName("active")
        val active: Int, // 1
        @SerializedName("created_at")
        val createdAt: String, // 2024-05-01 18:11:04
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("name")
        val name: String, // Travel
        @SerializedName("updated_at")
        val updatedAt: Any // null
    )
}