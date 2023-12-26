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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.education.network.model.Media;
import org.education.network.model.Post;
import org.education.network.model.User;
import org.education.network.model.profile.embedded.Education;
import org.education.network.model.profile.embedded.LastJob;
import org.education.network.model.profile.embedded.PersonMain;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"media", "languages", "posts"})
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
    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_profile_language",
            joinColumns = @JoinColumn(name = "user_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    private List<Language> languages;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userProfile", orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Post> posts;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userProfile", orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Media> media;

    public void addMedia(Media media) {
        media.setUserProfile(this);
        this.media.add(media);
    }

    public void deleteMedia(Media media) {
        this.media.remove(media);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile profile = (UserProfile) o;
        return Objects.equals(id, profile.id)
                && Objects.equals(personMain.getName(), profile.personMain.getName())
                && Objects.equals(personMain.getLastname(), profile.personMain.getLastname())
                && Objects.equals(personMain.getBirthDate(), profile.personMain.getBirthDate())
                && Objects.equals(lastjob.getCompany(), profile.lastjob.getCompany())
                && Objects.equals(lastjob.getTitle(), profile.lastjob.getTitle())
                && Objects.equals(education.getSpecialization(), profile.education.getSpecialization())
                && Objects.equals(education.getUniversity(), profile.education.getUniversity())
                && Objects.equals(location, profile.location)
                && Objects.equals(phoneNumber, profile.phoneNumber)
                && Objects.equals(languages, profile.languages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personMain, lastjob, education, location, phoneNumber, languages, posts, user, media);
    }


}
