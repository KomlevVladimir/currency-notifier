package com.vladimirkomlev.currencynotifier.service

import com.vladimirkomlev.currencynotifier.config.properties.AppProperties
import com.vladimirkomlev.currencynotifier.dto.CurrencyRate
import com.vladimirkomlev.currencynotifier.infra.email.EmailMessage
import com.vladimirkomlev.currencynotifier.infra.messaging.MessageQueues
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

const val EXPECTED_USD_KZT_RATE_TENGES = 370
const val EXPECTED_USD_RUB_RATE_RUBLES = 60

@Service
class CurrencyService(
        private val restTemplate: RestTemplate,
        private val appProperties: AppProperties,
        private val messagesQueues: MessageQueues
) {

    fun getExchangeRate(base: String, symbols: String) = restTemplate.getForObject<CurrencyRate>(
            "/api/latest.json?app_id={app_id}&base={base}&symbols={symbols}",
            "149d37dfdb14423d83255858a2f826c1",
            base,
            symbols
    )

    @Scheduled(cron = "\${exchange-rate.cron}")
    fun currencyRateNotify() {
        val currencyRateBaseOnUsd = getExchangeRate("USD", "KZT,RUB")
        val usdKzt = currencyRateBaseOnUsd.rates.kzt
        val usdRub = currencyRateBaseOnUsd.rates.rub
        if (usdKzt < EXPECTED_USD_KZT_RATE_TENGES) {
            messagesQueues.enqueueEmail(EmailMessage(
                    recipient = appProperties.recipientEmail,
                    subject = "Currency notifier",
                    message = "Usd exchange rate is $usdKzt tenges. Run to change tenges into dollars!"
            ))
        }
        if (usdRub < EXPECTED_USD_RUB_RATE_RUBLES) {
            messagesQueues.enqueueEmail(EmailMessage(
                    recipient = appProperties.recipientEmail,
                    subject = "Currency notifier",
                    message = "Usd exchange rate is $usdRub rubles. Run to change rubles into dollars!"
            ))
        }
    }
}