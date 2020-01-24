package com.vladimirkomlev.currencynotifier.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.vladimirkomlev.currencynotifier.config.properties.RabbitProperties
import org.springframework.amqp.core.*
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig(private val rabbitProperties: RabbitProperties) {

    @Bean
    fun jackson2MessageConverter(objectMapper: ObjectMapper): Jackson2JsonMessageConverter {
        return Jackson2JsonMessageConverter(objectMapper)
    }

    @Bean
    fun emailsQueue(): Queue {
        return QueueBuilder.durable(rabbitProperties.queueName).build()
    }

    @Bean
    fun emailsExchange(): Exchange {
        return ExchangeBuilder.topicExchange(rabbitProperties.exchangeName).build()
    }

    @Bean
    fun emailBinding(emailsQueue: Queue, emailsExchange: Exchange): Binding {
        return BindingBuilder.bind(emailsQueue).to(emailsExchange).with(rabbitProperties.queueName).noargs()
    }
}