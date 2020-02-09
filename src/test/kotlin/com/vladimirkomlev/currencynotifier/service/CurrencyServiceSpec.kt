package com.vladimirkomlev.currencynotifier.service

import com.vladimirkomlev.currencynotifier.config.properties.AppProperties
import com.vladimirkomlev.currencynotifier.dto.CurrencyRate
import com.vladimirkomlev.currencynotifier.dto.Rate
import com.vladimirkomlev.currencynotifier.infra.messaging.MessageQueues
import io.kotlintest.assertSoftly
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.springframework.web.client.RestTemplate

class CurrencyServiceSpec : StringSpec({
    val restTemplate = mockk<RestTemplate>()
    val appProperties = mockk<AppProperties>()


    "Currency rate notify with usdRub less than 60 and usdKzt more than 370" {
        val mockCurrencyRate = CurrencyRate(
                disclaimer = "test",
                license = "test",
                timestamp = 1581249563,
                base = "USD",
                rates = Rate(kzt = 370.1, rub = 59.9)
        )
        every { appProperties.recipientEmail } returns "test@mailinator.com"
        val messageQueues = mockk<MessageQueues>()
        every { messageQueues.enqueueEmail(any()) } returns Unit
        val service = spyk(CurrencyService(restTemplate, appProperties, messageQueues))
        every { service.getExchangeRate("USD", "KZT,RUB") } returns mockCurrencyRate
        service.currencyRateNotify()

        assertSoftly {
            verify(exactly = 1) { messageQueues.enqueueEmail(any()) }
            verify(exactly = 1) { service.getExchangeRate("USD", "KZT,RUB") }
        }
    }

    "Currency rate notify with usdRub more than 60 and usdKzt less than 370" {
        val mockCurrencyRate = CurrencyRate(
                disclaimer = "test",
                license = "test",
                timestamp = 1581249563,
                base = "USD",
                rates = Rate(kzt = 369.9, rub = 60.1)
        )
        every { appProperties.recipientEmail } returns "test@mailinator.com"
        val messageQueues = mockk<MessageQueues>()
        every { messageQueues.enqueueEmail(any()) } returns Unit
        val service = spyk(CurrencyService(restTemplate, appProperties, messageQueues))
        every { service.getExchangeRate("USD", "KZT,RUB") } returns mockCurrencyRate
        service.currencyRateNotify()

        assertSoftly {
            verify(exactly = 1) { messageQueues.enqueueEmail(any()) }
            verify(exactly = 1) { service.getExchangeRate("USD", "KZT,RUB") }
        }
    }

    "Currency rate notify with usdRub less than 60 and usdKzt less than 370" {
        val mockCurrencyRate = CurrencyRate(
                disclaimer = "test",
                license = "test",
                timestamp = 1581249563,
                base = "USD",
                rates = Rate(kzt = 369.9, rub = 59.9)
        )
        every { appProperties.recipientEmail } returns "test@mailinator.com"
        val messageQueues = mockk<MessageQueues>()
        every { messageQueues.enqueueEmail(any()) } returns Unit
        val service = spyk(CurrencyService(restTemplate, appProperties, messageQueues))
        every { service.getExchangeRate("USD", "KZT,RUB") } returns mockCurrencyRate
        service.currencyRateNotify()

        assertSoftly {
            verify(exactly = 2) { messageQueues.enqueueEmail(any()) }
            verify(exactly = 1) { service.getExchangeRate("USD", "KZT,RUB") }
        }
    }

    "Currency rate notify with usdRub more than 60 and usdKzt more than 370" {
        val mockCurrencyRate = CurrencyRate(
                disclaimer = "test",
                license = "test",
                timestamp = 1581249563,
                base = "USD",
                rates = Rate(kzt = 370.1, rub = 60.1)
        )
        every { appProperties.recipientEmail } returns "test@mailinator.com"
        val messageQueues = mockk<MessageQueues>()
        every { messageQueues.enqueueEmail(any()) } returns Unit
        val service = spyk(CurrencyService(restTemplate, appProperties, messageQueues))
        every { service.getExchangeRate("USD", "KZT,RUB") } returns mockCurrencyRate
        service.currencyRateNotify()

        assertSoftly {
            verify(exactly = 0) { messageQueues.enqueueEmail(any()) }
            verify(exactly = 1) { service.getExchangeRate("USD", "KZT,RUB") }
        }
    }
})