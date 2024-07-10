package com.example.billing_expertz.Model


import com.google.gson.annotations.SerializedName

data class SettingsBean(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String // Data loaded successfully.
) {
    data class Data(
        @SerializedName("logo")
        val logo: String // https://project-evaluation.clikzopdevp.com/logo/3301967979936881-whatsapp-image-2024-05-23-at-17.05.33-(3).jpeg
    )
}