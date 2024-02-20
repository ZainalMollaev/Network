package org.education.network.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.education.network.enumtypes.LikeAction;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeRequestDto {

    private LikeAction act;
    private String postId;

}
