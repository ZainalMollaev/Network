package org.education.network.model.profile;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class Language {

    @EmbeddedId
    private LanguageId languageId;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    @Embeddable
    public static class LanguageId implements Serializable {

        private static final long serialVersionUID = 1234567L;

        @Column(name = "user_profile_id")
        private Long id;
        private String name;

    }

    @OneToOne
    @MapsId("id")
    private UserProfile userProfile;

}