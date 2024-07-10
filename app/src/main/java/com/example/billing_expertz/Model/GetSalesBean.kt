package com.example.billing_expertz.Model


import com.google.gson.annotations.SerializedName

data class GetSalesBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String // Data loaded successfully.
) {
    data class Data(
        @SerializedName("admin_copy")
        val adminCopy: String, // https://billingfive.wellnessexpertz.com/view-invoice-bill?id=4
        @SerializedName("amt")
        val amt: String, // 420.00
        @SerializedName("company_id")
        val companyId: Int, // 1
        @SerializedName("created_at")
        val createdAt: String, // 2024-05-21 16:44:21
        @SerializedName("customer")
        val customer: String, // Customer 2
        @SerializedName("customer_copy")
        val customerCopy: String, // https://billingfive.wellnessexpertz.com/view-invoice-bill-customer?id=4
        @SerializedName("customer_id")
        val customerId: Int, // 2
        @SerializedName("customer_invoice")
        val customerInvoice: Int, // 0
        @SerializedName("due_date")
        val dueDate: String, // 0000-00-00
        @SerializedName("gst_type")
        val gstType: String, // Inner GST
        @SerializedName("id")
        val id: Int, // 4
        @SerializedName("invoice")
        val invoice: String, // INV_9
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
        @SerializedName("user")
        val user: String, // Admin
        @SerializedName("user_id")
        val userId: Int // 1
    )
}