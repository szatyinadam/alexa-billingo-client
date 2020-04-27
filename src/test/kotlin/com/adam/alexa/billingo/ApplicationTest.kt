package com.adam.alexa.billingo

import com.adam.alexa.billingo.client.BillingoService
import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.Intent
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.RequestEnvelope
import com.amazon.ask.model.Slot
import io.kotlintest.matchers.string.shouldContain
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import io.mockk.every
import io.mockk.mockk

class ApplicationTest : BehaviorSpec({

    given("the application") {
        val service = mockk<BillingoService>()
        every { service.sumByYear(any()) } returns 856_000
        val application = Application(service)

        `when`("the user would like to know his annual income") {
            val response = application.billingoHandler(createRequest())

            then("the response speech should contains it") {
                response.isPresent shouldBe true
                val outputSpeech = response.get().outputSpeech
                outputSpeech.toString() shouldContain "Your income in 2020 is 856000"
            }
        }
    }

})

private fun createRequest(): HandlerInput {
    val yearSlot = Slot.builder()
            .withName("year")
            .withValue("2020")
            .build()
    val slots = mapOf("year" to yearSlot)
    val intent = Intent.builder()
            .withSlots(slots)
            .build()
    val intentRequest = IntentRequest.builder()
            .withIntent(intent)
            .build()
    val requestEnvelope = RequestEnvelope.builder()
            .withRequest(intentRequest)
            .build()
    return HandlerInput.builder()
            .withRequestEnvelope(requestEnvelope)
            .build()
}


