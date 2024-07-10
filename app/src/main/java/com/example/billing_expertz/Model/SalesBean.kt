package com.example.billing_expertz.Model


import com.google.gson.annotations.SerializedName

data class SalesBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String // Data loaded successfully.
) {
    data class Data(
        @SerializedName("company_id")
        val companyId: Int, // 5
        @SerializedName("created_at")
        val createdAt: String, // 2024-05-21 16:25:41
        @SerializedName("customer_id")
        val customerId: Int, // 1
        @SerializedName("customer_invoice")
        val customerInvoice: Int, // 0
        @SerializedName("due_date")
        val dueDate: String, // 2024-05-21
        @SerializedName("gst_type")
        val gstType: String, // Outer GST
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("invoice")
        val invoice: String, // INV_6
        @SerializedName("invoice_date")
        val invoiceDate: String, // 2024-05-21
        @SerializedName("is_billed")
        val isBilled: String, // Billed
        @SerializedName("payment_status")
        val paymentStatus: String, // pending
        @SerializedName("service_tax")
        val serviceTax: String, // 5.00
        @SerializedName("updated_at")
        val updatedAt: Any, // null
        @SerializedName("user_id")
        val userId: Int // 1
    )
}