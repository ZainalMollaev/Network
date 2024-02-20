package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrokerService {

    private final RabbitTemplate rabbitTemplate;

    public void distributor(String message, String key, String exchange) {
        rabbitTemplate.convertAndSend(exchange, key, message);
    }

}
