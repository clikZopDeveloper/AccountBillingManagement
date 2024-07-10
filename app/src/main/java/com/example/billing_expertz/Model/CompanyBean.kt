package com.example.billing_expertz.Model


import com.google.gson.annotations.SerializedName

data class CompanyBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String // Data loadedsuccessfully.
) {
    data class Data(
        @SerializedName("account_number")
        val accountNumber: String,
        @SerializedName("address")
        val address: String,
        @SerializedName("bank_name")
        val bankName: String,
        @SerializedName("created_at")
        val createdAt: String, // 2024-05-0118:07:12
        @SerializedName("email")
        val email: String,
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("ifsc")
        val ifsc: String,
        @SerializedName("invoice_no")
        val invoiceNo: Int, // 8
        @SerializedName("invoice_prefix")
        val invoicePrefix: String, // XYZ
        @SerializedName("name")
        val name: String, // XYZ
        @SerializedName("number")
        val number: String,
        @SerializedName("updated_at")
        val updatedAt: String // 2024-05-29 11:41:12
    )
}