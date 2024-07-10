package com.example.billing_expertz.Model


import com.google.gson.annotations.SerializedName

data class GetExpensesBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String // Data loaded successfully.
) {
    data class Data(
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
        val name: String, // test
        @SerializedName("note")
        val note: String, // test
        @SerializedName("payment_mode")
        val paymentMode: String, // Cash
        @SerializedName("ref_no")
        val refNo: String, // ref
        @SerializedName("trans_id")
        val transId: String,
        @SerializedName("updated_at")
        val updatedAt: Any, // null
        @SerializedName("user")
        val user: String, // User 1
        @SerializedName("user_id")
        val userId: Int, // 7
        @SerializedName("vendor_id")
        val vendorId: Int, // 1
        @SerializedName("vendor_name")
        val vendorName: String // Test
    )
}