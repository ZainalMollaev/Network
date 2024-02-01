package org.education.network.dto.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class SubscriptionDto {

    private String firstname;
    private String lastname;
    private String company;
    private String title;
    private String location;
    private List<SubPostDto> posts;
    private List<CommonSubs> commonSubs;


    public static class SubPostDto{
        private String id;
        private LocalDate creationDate;
        private byte[] img;
    }

    public static class CommonSubs {
        private byte[] miniAvatar;
        private String username;
    }
}

