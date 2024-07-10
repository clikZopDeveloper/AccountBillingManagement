package com.example.billing_expertz.Model


import com.google.gson.annotations.SerializedName

data class SubCategoryBean(
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
        @SerializedName("category_id")
        val categoryId: String, // Travel
        @SerializedName("created_at")
        val createdAt: String, // 2024-05-01 18:11:20
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("name")
        val name: String, // Cab
        @SerializedName("updated_at")
        val updatedAt: Any // null
    )
}