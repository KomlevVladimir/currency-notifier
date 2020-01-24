package com.vladimirkomlev.currencynotifier.infra.email

class EmailMessage(
        val recipient: String,
        val subject: String,
        val message: String
)