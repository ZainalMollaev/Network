package org.education.network.model.post;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.education.network.enumtypes.Privacies;
import org.education.network.model.profile.UserProfile;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.uuid.UuidGenerator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"privacy", "userProfile", "likes"})
@Entity
public class Post {

    @Id
    @GenericGenerator(name = "UUID",
            type = UuidGenerator.class)
    private UUID id;
    private String title;
    private String description;
    private String location;
    private LocalDate creationDate;
    @Enumerated(EnumType.STRING)
    private Privacies privacy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id",
            nullable = false)
    private UserProfile userProfile;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    @Builder.Default
    private Set<Like> likes = new HashSet<>();

}
