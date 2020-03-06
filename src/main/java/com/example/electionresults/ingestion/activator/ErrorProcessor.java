package com.example.electionresults.ingestion.activator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.mail.MailSendingMessageHandler;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
public class ErrorProcessor {

    Logger logger = LoggerFactory.getLogger(ElectionResultProcessor.class);

    @Value( "${electionResult.mail.from}" )
    private String mailFrom;

    @Value( "${electionResult.mail.to}" )
    private String mailTo;

    @Autowired
    private JavaMailSender mailSender;

    @ServiceActivator(inputChannel = "errorChannel", outputChannel = "emailChannel")
    public MailMessage buildEmail(MessagingException messagingException) {

        logger.info("File error. Build email ");

        MailMessage mailMsg = new SimpleMailMessage();
        mailMsg.setFrom(mailFrom);
        mailMsg.setTo(mailTo);
        mailMsg.setSubject("Invalid file received");

        String mailText = "File " + messagingException.getFailedMessage().getHeaders().get(FileHeaders.FILENAME) + " is invalid";
        mailMsg.setText(mailText);

        return mailMsg;
    }

    @ServiceActivator(inputChannel = "emailChannel", outputChannel = "nullChannel")
    public MessageHandler mailsSenderMessagingHandler(Message<MailMessage> message) {

        logger.info("File error. Sending email");

        MailSendingMessageHandler mailSendingMessageHandler = new MailSendingMessageHandler(mailSender);
        mailSendingMessageHandler.handleMessage(message);
        return mailSendingMessageHandler;
    }
}