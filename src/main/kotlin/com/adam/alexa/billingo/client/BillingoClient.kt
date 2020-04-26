package com.adam.alexa.billingo.client

import com.fasterxml.jackson.databind.DeserializationFeature
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.client.annotation.Client
import io.micronaut.jackson.annotation.JacksonFeatures

@Client("https://www.billingo.hu")
@JacksonFeatures(
        disabledDeserializationFeatures = [DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES]
)
interface BillingoClient {
    @Get("/api/invoices/query?year_start={yearStart}&year_end={yearEnd}&page=1")
    fun getInvoices(
            @Header("Authorization") token: String,
            yearStart: Int,
            yearEnd: Int
    ): ApiResponse<Invoice>
}