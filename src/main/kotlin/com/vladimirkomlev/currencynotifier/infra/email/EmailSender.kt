package com.vladimirkomlev.currencynotifier.infra.email

import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
@RabbitListener(queues = ["\${rmq.email.queue-name}"])
class EmailSender(
        @Value("\${spring.mail.username}") private val username: String,
        private val mailSender: JavaMailSender
) {

    @RabbitHandler
    fun sendEmail(emailMessage: EmailMessage) {
        val message = SimpleMailMessage()
        message.setTo(emailMessage.recipient)
        message.setFrom(username)
        message.setSubject(emailMessage.subject)
        message.setText(emailMessage.message)
        mailSender.send(message)
    }
}