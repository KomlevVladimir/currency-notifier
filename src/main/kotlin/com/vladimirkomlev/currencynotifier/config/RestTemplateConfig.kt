package com.vladimirkomlev.currencynotifier.config

import com.vladimirkomlev.currencynotifier.config.properties.AppProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfig(private val appProperties: AppProperties) {

    @Bean
    fun restTemplate(): RestTemplate =
            RestTemplateBuilder()
                    .rootUri(appProperties.cbrUrl)
                    .build()
}