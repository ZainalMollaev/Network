package org.education.network.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.education.network.model.profile.UserProfile;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Post {

    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private String title;
    private String description;
    private String location;
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "profile_id",
            nullable = false)
    private UserProfile userProfile;

}
