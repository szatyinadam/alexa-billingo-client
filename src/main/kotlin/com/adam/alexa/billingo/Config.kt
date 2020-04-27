package com.adam.alexa.billingo

import io.micronaut.context.annotation.ConfigurationProperties
import javax.validation.constraints.NotBlank

@ConfigurationProperties("billingo")
class Config {

    @NotBlank
    var apiKey = "API key"

    @NotBlank
    var secret = "Secret"

}