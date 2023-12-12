package org.education.network.service.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Order(Ordered.LOWEST_PRECEDENCE)
public class DefaultProfileService extends UserProfileService{

    @Override
    public void delete(String email) {
    }

    @Override
    public void update(String email, String photoID) {
    }

    @Override
    public boolean support(String method) {
        return true;
    }

    @Override
    public String getPhoto(String email) {
        return "No such method";
    }
}
