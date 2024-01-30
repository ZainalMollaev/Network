package org.education.network.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.education.network.enumtypes.Roles;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtDto {

    private String username;
    private List<Roles> roles;

    @Override
    public String toString() {
        return "JwtDto{" +
                "username='" + username + '\'' +
                ", roles=" + roles +
                '}';
    }
}
