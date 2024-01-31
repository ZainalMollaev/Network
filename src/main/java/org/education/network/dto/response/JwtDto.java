package org.education.network.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.education.network.enumtypes.Roles;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtDto {

    private String username;
    private Roles role;


}
