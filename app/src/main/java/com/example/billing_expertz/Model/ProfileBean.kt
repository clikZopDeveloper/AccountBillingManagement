package com.example.billing_expertz.Model


import com.google.gson.annotations.SerializedName

data class ProfileBean(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String // Data Load Successfully
):java.io.Serializable {
    data class Data(
        @SerializedName("address")
        val address: Any, // null
        @SerializedName("client_id")
        val clientId: Int, // 1
        @SerializedName("company_id")
        val companyId: Int, // 0
        @SerializedName("created_date")
        val createdDate: String, // 2022-05-2217:52:14
        @SerializedName("email")
        val email: String, // manmeet@clikzopinnovations.com
        @SerializedName("id")
        val id: Int, // 3
        @SerializedName("is_active")
        val isActive: Int, // 1
        @SerializedName("last_ipaddress")
        val lastIpaddress: String, // ::1
        @SerializedName("last_login")
        val lastLogin: String, // 2024-03-1217:33:45
        @SerializedName("last_updated")
        val lastUpdated: String, // 2022-05-22 17:52:14
        @SerializedName("mobile")
        val mobile: String, // 9711428904
        @SerializedName("name")
        val name: String, // Manmeet
        @SerializedName("no_of_agents")
        val noOfAgents: Int, // 0
        @SerializedName("password")
        val password: String, // !Admin@2021
        @SerializedName("profile_pic")
        val profilePic: String,
        @SerializedName("token")
        val token: String, // g2Y90HGLvbGv
        @SerializedName("user_type")
        val userType: String // agent
    ):java.io.Serializable
}