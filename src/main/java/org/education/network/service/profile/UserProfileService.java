package org.education.network.service.profile;

public abstract class UserProfileService {

    public abstract void delete(String email);
    public abstract void update(String email, String photoID);
    public abstract boolean support(String method);
    public abstract String getPhoto(String email);

}
