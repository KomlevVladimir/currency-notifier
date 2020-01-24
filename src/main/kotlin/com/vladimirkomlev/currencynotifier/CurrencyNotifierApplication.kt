package com.vladimirkomlev.currencynotifier

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class CurrencyNotifierApplication

fun main(args: Array<String>) {
	runApplication<CurrencyNotifierApplication>(*args)
}
