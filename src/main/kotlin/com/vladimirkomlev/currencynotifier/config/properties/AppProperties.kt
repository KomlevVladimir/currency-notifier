package com.vladimirkomlev.currencynotifier.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties
class AppProperties {
    lateinit var cbrUrl: String
    lateinit var recipientEmail: String
}