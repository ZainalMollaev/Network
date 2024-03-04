package org.education.network.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.education.network.dto.bd.LikeDto;
import org.education.network.dto.request.LikeRequestDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final BrokerService brokerService;
    private final ObjectMapper om;

    public void likeOperation(LikeRequestDto likeRequestDto, String username) {
        LikeDto likeBrokerDto = LikeDto.builder()
                .likeAct(likeRequestDto.getAct())
                .postId(likeRequestDto.getPostId())
                .username(username)
                .build();

        try {
            brokerService.distributor(om.writeValueAsString(likeBrokerDto), "likes", "postExchange");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
