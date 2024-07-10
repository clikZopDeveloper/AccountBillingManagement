package com.example.billing_expertz.Model


import com.google.gson.annotations.SerializedName

data class CustomerBean(
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
        val createdAt: String, // 2024-05-01 18:10:24
        @SerializedName("email")
        val email: String,
        @SerializedName("gst_no")
        val gstNo: String,
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("name")
        val name: String, // Customer 1
        @SerializedName("number")
        val number: String, // 7897897890
        @SerializedName("pan_number")
        val panNumber: String,
        @SerializedName("pincode")
        val pincode: Any, // null
        @SerializedName("state")
        val state: Any, // null
        @SerializedName("updated_at")
        val updatedAt: String, // 2024-05-08 16:07:07
        @SerializedName("user_id")
        val userId: Int, // 10
        @SerializedName("wallet_amt")
        val walletAmt: String // 0.00
    )
}