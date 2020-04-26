package com.adam.alexa.billingo.client

import com.fasterxml.jackson.annotation.JsonProperty

data class ApiResponse<T>(
        @JsonProperty("success") val success: Boolean,
        @JsonProperty("type") val type: String,
        @JsonProperty("data") val data: List<Item<T>>
)

data class Item<T>(
        @JsonProperty("id") val id: Long,
        @JsonProperty("attributes") val attributes: T
)

data class Invoice(
        @JsonProperty("invoice_no") val invoiceNo: String,
        @JsonProperty("date") val date: String,
        @JsonProperty("fulfillment_date") val fulfillmentDate: String,
        @JsonProperty("due_date") val dueDate: String,
        @JsonProperty("total") val total: Double,
        @JsonProperty("total_paid") val totalPaid: Double,
        @JsonProperty("net_total") val netTotal: Double
)