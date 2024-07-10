package com.example.billing_expertz.Model


import com.google.gson.annotations.SerializedName

data class VendorLabourBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String // Data loaded successfully.
) {
    data class Data(
        @SerializedName("address")
        val address: String,
        @SerializedName("city")
        val city: String,
        @SerializedName("country")
        val country: String, // India
        @SerializedName("created_at")
        val createdAt: String, // 2024-05-01 18:07:48
        @SerializedName("email")
        val email: String,
        @SerializedName("gst")
        val gst: String, // 0.00
        @SerializedName("id")
        val id: Int, // 2
        @SerializedName("name")
        val name: String, // Labor 1
        @SerializedName("number")
        val number: String, // 9089089089
        @SerializedName("pan_number")
        val panNumber: String,
        @SerializedName("pincode")
        val pincode: String,
        @SerializedName("state")
        val state: Any, // null
        @SerializedName("updated_at")
        val updatedAt: String, // 2024-05-01 18:10:03
        @SerializedName("user_id")
        val userId: Int, // 1
        @SerializedName("user_type")
        val userType: String // labour
    )
}