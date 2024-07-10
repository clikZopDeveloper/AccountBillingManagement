package com.example.billing_expertz.Model


import com.google.gson.annotations.SerializedName

data class LoginBean(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String // User logged in successfully.
) {
    data class Data(
        @SerializedName("address")
        val address: String, // Chd
        @SerializedName("city")
        val city: String, // Chandigarh
        @SerializedName("created_at")
        val createdAt: String, // 2024-05-01 17:54:03
        @SerializedName("email")
        val email: String, // user1@gmail.com
        @SerializedName("id")
        val id: Int, // 7
        @SerializedName("joining_date")
        val joiningDate: String, // 2024-05-01
        @SerializedName("last_ip")
        val lastIp: String, // ::1
        @SerializedName("last_login")
        val lastLogin: String, // 2024-06-28 18:01:15
        @SerializedName("mobile")
        val mobile: String, // 9876549876
        @SerializedName("name")
        val name: String, // User 1
        @SerializedName("password")
        val password: String, // 123456
        @SerializedName("role")
        val role: String, // Staff
        @SerializedName("state")
        val state: String, // Chandigarh
        @SerializedName("status")
        val status: Int, // 1
        @SerializedName("token")
        val token: String, // vmfibzkeeLsa
        @SerializedName("username")
        val username: String // User 1
    )
}