package com.example.billing_expertz.Model


import com.google.gson.annotations.SerializedName

data class DashboardBean(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String // Data loaded successfully.
) {
    data class Data(
        @SerializedName("expenses")
        val expenses: List<Expense>,
        @SerializedName("sales")
        val sales: List<Sale>,
        @SerializedName("total_customer")
        val totalCustomer: TotalCustomer,
        @SerializedName("total_expense")
        val totalExpense: TotalExpense,
        @SerializedName("total_sale")
        val totalSale: TotalSale,
        @SerializedName("total_vendor")
        val totalVendor: TotalVendor
    ) {
        data class Expense(
            @SerializedName("amount")
            val amount: String, // 20000.00
            @SerializedName("created_at")
            val createdAt: String, // 2024-07-02 18:28:00
            @SerializedName("customer_name")
            val customerName: String, // Customer 1
            @SerializedName("expense_category")
            val expenseCategory: String, // Travel
            @SerializedName("expense_date")
            val expenseDate: String, // 2024-07-02
            @SerializedName("expenses_for")
            val expensesFor: Any, // null
            @SerializedName("file")
            val `file`: String,
            @SerializedName("id")
            val id: Int, // 4
            @SerializedName("ids")
            val ids: Int, // 1
            @SerializedName("name")
            val name: String, // ee
            @SerializedName("note")
            val note: String, // dsdfs
            @SerializedName("payment_mode")
            val paymentMode: String, // Cash
            @SerializedName("ref_no")
            val refNo: String, // ref
            @SerializedName("trans_id")
            val transId: String,
            @SerializedName("updated_at")
            val updatedAt: String, // 2024-07-03 13:34:52
            @SerializedName("user")
            val user: String, // User 1
            @SerializedName("user_id")
            val userId: Int, // 7
            @SerializedName("vendor_id")
            val vendorId: Int, // 1
            @SerializedName("vendor_name")
            val vendorName: String // Test
        )

        data class Sale(
            @SerializedName("admin_copy")
            val adminCopy: String, // https://crm.kunalrealtyservices.com/view-invoice-bill?id=7
            @SerializedName("created_at")
            val createdAt: String, // 2024-07-03 11:01:18
            @SerializedName("customer_copy")
            val customerCopy: String, // https://crm.kunalrealtyservices.com/view-invoice-bill-customer?id=7
            @SerializedName("customer_id")
            val customerId: Int, // 1
            @SerializedName("customer_invoice")
            val customerInvoice: Int, // 0
            @SerializedName("due_date")
            val dueDate: String, // 2024-07-04
            @SerializedName("gst_type")
            val gstType: String, // Inner GST
            @SerializedName("id")
            val id: Int, // 7
            @SerializedName("amt")
            val amt: String, // 420.00
            @SerializedName("invoice")
            val invoice: String, // INV_8
            @SerializedName("invoice_date")
            val invoiceDate: String, // 2024-07-04
            @SerializedName("payment_status")
            val paymentStatus: String, // pending
            @SerializedName("service_tax")
            val serviceTax: String, // 0.00
            @SerializedName("updated_at")
            val updatedAt: String, // 2024-07-03 13:35:23
            @SerializedName("user_id")
            val userId: Int // 7
        )

        data class TotalCustomer(
            @SerializedName("total_customer")
            val totalCustomer: Int // 1
        )

        data class TotalExpense(
            @SerializedName("total_expense")
            val totalExpense: String // 25012.00
        )

        data class TotalSale(
            @SerializedName("total_sale")
            val totalSale: String // 241544.00
        )

        data class TotalVendor(
            @SerializedName("total_vendor")
            val totalVendor: Int // 1
        )
    }
}