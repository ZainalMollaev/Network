package org.education.network.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.education.network.dto.bd.LikeDto;
import org.education.network.service.LikeService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
@RequiredArgsConstructor
public class RabbitMQConsumer {

    private final ObjectMapper om;
    private final LikeService likeService;

    @SneakyThrows
    @RabbitListener(queues = "likes")
    public void processLikesQueue(String message) {
        LikeDto likeBrokerDto = om.readValue(message, LikeDto.class);
        likeService.saveLike(likeBrokerDto);
    }



}
