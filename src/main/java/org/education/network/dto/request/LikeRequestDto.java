package org.education.network.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.education.network.enumtypes.LikeAct;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeRequestDto {

    private LikeAct act;
    private String postId;

}
