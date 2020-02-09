package com.vladimirkomlev.currencynotifier.infra

import com.vladimirkomlev.currencynotifier.infra.email.EmailMessage
import com.vladimirkomlev.currencynotifier.infra.email.EmailSender
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender

class EmailSenderSpec : StringSpec({
    "Send email" {
        val javaMailSender = mockk<JavaMailSender>()
        val emailMessage = EmailMessage("test", "test", "test")
        val message = SimpleMailMessage().apply {
            setTo(emailMessage.recipient)
            setFrom("")
            setSubject(emailMessage.subject)
            setText(emailMessage.message)
        }
        every { javaMailSender.send(message) } returns Unit
        val emailSender = EmailSender("", javaMailSender)
        emailSender.sendEmail(emailMessage)

        verify { javaMailSender.send(message) }
    }
})