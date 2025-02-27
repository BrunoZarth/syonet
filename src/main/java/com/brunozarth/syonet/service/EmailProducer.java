package com.brunozarth.syonet.service;

import com.brunozarth.syonet.DTO.EmailMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.brunozarth.syonet.config.RabbitMQConfig.EMAIL_QUEUE;

@Service
@RequiredArgsConstructor
public class EmailProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendEmailToQueue(EmailMessageDTO emailMessageDTO) {
        rabbitTemplate.convertAndSend(EMAIL_QUEUE, emailMessageDTO);
    }
}
