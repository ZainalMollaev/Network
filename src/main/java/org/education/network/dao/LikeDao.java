package org.education.network.dao;

import lombok.RequiredArgsConstructor;
import org.education.network.dto.bd.LikeDto;
import org.education.network.mapping.LikeMapper;
import org.education.network.model.repository.LikeRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeDao {

    private final LikeRepository repository;
    private final LikeMapper mapper;

    public void saveLike(LikeDto likeBrokerDto) {
        repository.save( mapper.toEntity(likeBrokerDto));
    }

}
