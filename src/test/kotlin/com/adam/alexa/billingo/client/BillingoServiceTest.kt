package com.adam.alexa.billingo.client

import com.adam.alexa.billingo.Config
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class BillingoServiceTest : BehaviorSpec({

    given("a service with a client") {
        val client = mockk<BillingoClient>()
        every { client.getInvoices(any(), 2020, 2020) } returns mockResponse()
        every { client.getInvoices(any(), 2019, 2019) } returns emptyMockResponse()
        val service = BillingoService(client, Config())

        `when`("get the sum of 2020 net income") {
            val sumOf2020 = service.sumByYear(2020)

            then("the result should be 856 000") {
                sumOf2020 shouldBe 856_000 // 300000 + 500000 + 56000
            }
        }
        `when`("get the sum of 2019 net income") {
            service.sumByYear(2019)

            then("the API should be called with 2019 start and end year") {
                verify(exactly = 1) {
                    client.getInvoices(any(), 2019, 2019)
                }
            }
        }
    }

})

private fun emptyMockResponse(): ApiResponse<Invoice> = ApiResponse(true, "invoices", emptyList())

private fun mockResponse(): ApiResponse<Invoice> {
    val item1 = Item(1357457845, Invoice(
            "2020-000001",
            "2020-02-13",
            "2020-01-30",
            "2020-02-27",
            381000.0,
            381000.0,
            300000.0
    ))
    val item2 = Item(20955197, Invoice(
            "2020-000001",
            "2020-02-19",
            "2020-02-18",
            "2020-02-26",
            635000.0,
            635000.0,
            500000.0
    ))
    val item3 = Item(1605035432, Invoice(
            "2020-000001",
            "2020-03-02",
            "2020-03-02",
            "2020-03-10",
            71120.0,
            71120.0,
            56000.0
    ))
    val item4 = Item(1323967472, Invoice(
            "2020-000002",
            "2020-04-02",
            "2020-03-31",
            "2020-04-10",
            1270000.0,
            0.0,
            1000000.0
    ))
    return ApiResponse(true, "invoices", listOf(item1, item2, item3, item4))
}
