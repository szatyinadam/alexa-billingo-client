package com.adam.alexa.billingo.client

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.apache.commons.codec.digest.DigestUtils
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class TokenCreator(private val apiKey: String, private val secret: String) {

    fun create(): String {
        val issuedAt = Date()
        val jti: String = DigestUtils.md5Hex((apiKey + issuedAt.time).toByteArray())
        val expirationDate = Date.from(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault()).toInstant())
        return "Bearer " + Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .signWith(SignatureAlgorithm.HS256, secret.toByteArray())
                .setSubject(apiKey)
                .setIssuedAt(issuedAt)
                .setExpiration(expirationDate)
                .claim("jti", jti)
                .compact()
    }

}
