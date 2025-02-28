package com.brunozarth.syonet.service;

import com.brunozarth.syonet.DTO.EmailMessageDTO;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailConsumer {

    private final JavaMailSender mailSender;

    @RabbitListener(queues = "emailQueue")
    public void processEmail(EmailMessageDTO emailMessageDTO) {
        log.info("Receiving queue messages: {}", emailMessageDTO);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(emailMessageDTO.getTo());
            helper.setSubject(emailMessageDTO.getSubject());
            helper.setText(emailMessageDTO.getBody(), true);

            mailSender.send(message);
            log.info("E-mail send successfully: {}", emailMessageDTO.getTo());
        } catch (Exception e) {
            log.error("E-mail send fail", e);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}

