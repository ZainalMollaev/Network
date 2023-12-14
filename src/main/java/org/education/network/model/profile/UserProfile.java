package org.education.network.model.profile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.education.network.model.Media;
import org.education.network.model.User;
import org.education.network.model.profile.embedded.Education;
import org.education.network.model.profile.embedded.LastJob;
import org.education.network.model.profile.embedded.PersonMain;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"user", "languages"})
@Builder
@Entity
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private PersonMain personMain;
    @Embedded
    private LastJob lastjob;
    @Embedded
    private Education education;
    private String location;
    private UUID avatar;
    private UUID backPhoto;
    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_profile_language",
            joinColumns = @JoinColumn(name = "user_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    private List<Language> languages;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "media_id", referencedColumnName = "id")
    private Media media;

}
