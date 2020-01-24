package com.vladimirkomlev.currencynotifier.infra.messaging

import com.vladimirkomlev.currencynotifier.config.properties.RabbitProperties
import com.vladimirkomlev.currencynotifier.infra.email.EmailMessage
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class MessageQueues(
        private val rabbitTemplate: RabbitTemplate,
        private val rabbitProperties: RabbitProperties
) {

    fun enqueueEmail(message: EmailMessage) {
        rabbitTemplate.convertAndSend(rabbitProperties.queueName, message)
    }
}