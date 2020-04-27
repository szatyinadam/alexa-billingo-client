package com.adam.alexa.billingo.client

import com.adam.alexa.billingo.Config
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BillingoService(@Inject private val billingoClient: BillingoClient,
                      @Inject private val config: Config) {

    fun sumByYear(year: Int): Int {
        val token = TokenCreator(config.apiKey, config.secret).create()
        val invoices = billingoClient.getInvoices(token, year, year)

        return invoices.data
                .filter { it.attributes.totalPaid == it.attributes.total }
                .sumBy { it.attributes.netTotal.toInt() }
    }

}
