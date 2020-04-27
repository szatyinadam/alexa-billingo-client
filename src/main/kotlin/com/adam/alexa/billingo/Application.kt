package com.adam.alexa.billingo

import com.adam.alexa.billingo.client.BillingoService
import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import io.micronaut.function.aws.alexa.annotation.IntentHandler
import java.time.LocalDate
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Application(@Inject private val billingoService: BillingoService) {

    @IntentHandler("BillingoIntent")
    fun billingoHandler(input: HandlerInput): Optional<Response> {
        val intentRequest = input.request as IntentRequest
        val year = intentRequest.intent.slots["year"]?.value?.toInt()
        val queryYear = year ?: LocalDate.now().year
        val sum = billingoService.sumByYear(queryYear)

        val speechText = "Your income in $queryYear is $sum"
        return input.responseBuilder
                .withSpeech(speechText)
                .withSimpleCard("$queryYear income", speechText)
                .build()
    }

}

