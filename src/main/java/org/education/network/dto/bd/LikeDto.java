package org.education.network.dto.bd;

import lombok.Builder;
import lombok.Data;
import org.education.network.dto.request.LikeRequestDto;
import org.education.network.enumtypes.LikeAction;

@Data
public class LikeDto extends LikeRequestDto {

    private String username;

    @Builder
    public LikeDto(LikeAction likeAct, String postId, String username){
        super(likeAct, postId);
        this.username = username;
    }
}
