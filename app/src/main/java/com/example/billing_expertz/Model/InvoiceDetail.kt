package com.example.billing_expertz.Model


import com.google.gson.annotations.SerializedName

data class InvoiceDetail(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String // Data loaded successfully.
) {
    data class Data(
        @SerializedName("order_det")
        val orderDet: List<OrderDet>,
        @SerializedName("order_mst")
        val orderMst: OrderMst
    ) {
        data class OrderDet(
            @SerializedName("category")
            val category: String, // WEEDDINGS
            @SerializedName("commision")
            val commision: String, // 0.00
            @SerializedName("created_at")
            val createdAt: String, // 2024-06-28 10:51:10
            @SerializedName("description")
            val description: String,
            @SerializedName("gst")
            val gst: String, // 0.00
            @SerializedName("gst_type")
            val gstType: String, // null
            @SerializedName("id")
            val id: Int, // 13
            @SerializedName("order_id")
            val orderId: Int, // 14
            @SerializedName("price")
            val price: String, // 1000.00
            @SerializedName("qty")
            val qty: Int, // 50
            @SerializedName("service_tax_applicable")
            val serviceTaxApplicable: Int, // 0
            @SerializedName("sub_category")
            val subCategory: String, // RECEPTION
            @SerializedName("updated_at")
            val updatedAt: String, // null
            @SerializedName("user_id")
            val userId: Int // 12
        )

        data class OrderMst(
            @SerializedName("commission")
            val commission: String, // 0.00
            @SerializedName("company_id")
            val companyId: Int, // 1
            @SerializedName("created_at")
            val createdAt: String, // 2024-06-28 10:51:10
            @SerializedName("customer")
            val customer: String, // SEEMA
            @SerializedName("customer_id")
            val customerId: Int, // 1
            @SerializedName("customer_invoice")
            val customerInvoice: Int, // 0
            @SerializedName("due_date")
            val dueDate: String, // 0000-00-00
            @SerializedName("event_date")
            val eventDate: String, // null
            @SerializedName("event_shift")
            val eventShift: String,
            @SerializedName("extra_by_guest")
            val extraByGuest: String, // null
            @SerializedName("file")
            val `file`: String, // null
            @SerializedName("gst")
            val gst: String, // 0.00
            @SerializedName("gst_type")
            val gstType: String,
            @SerializedName("id")
            val id: Int, // 14
            @SerializedName("illusions")
            val illusions: String, // null
            @SerializedName("invoice")
            val invoice: String, // hoc_6
            @SerializedName("invoice_date")
            val invoiceDate: String, // 0000-00-00
            @SerializedName("is_billed")
            val isBilled: String,
            @SerializedName("occasions")
            val occasions: String, // null
            @SerializedName("payment_status")
            val paymentStatus: String, // pending
            @SerializedName("service_tax")
            val serviceTax: String, // 0.00
            @SerializedName("updated_at")
            val updatedAt: String, // 2024-06-28 12:52:00
            @SerializedName("user_id")
            val userId: Int, // 13
            @SerializedName("venue")
            val venue: String // null
        )
    }
}