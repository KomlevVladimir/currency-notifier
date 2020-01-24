package com.vladimirkomlev.currencynotifier.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("rmq.email")
class RabbitProperties {
    lateinit var queueName: String
    lateinit var exchangeName: String
}