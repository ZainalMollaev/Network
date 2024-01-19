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
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.education.network.model.Post;
import org.education.network.model.User;
import org.education.network.model.profile.embedded.Education;
import org.education.network.model.profile.embedded.LastJob;
import org.education.network.model.profile.embedded.PersonMain;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@NamedEntityGraph(
        name = "user-subscriptions-entity-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "subscribes"),
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"subscribes", "languages", "posts"})
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

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST} )
    @JoinTable(name="tbl_subscribers",
            joinColumns=@JoinColumn(name="userId"),
            inverseJoinColumns=@JoinColumn(name="subscriberId"),
            uniqueConstraints = {
                    @UniqueConstraint(columnNames = { "userId", "subscriberId" })}
    )
    @Builder.Default
    private Set<UserProfile> subscribes = new HashSet<>();

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    //todo Реализовать количество подписчиков и количество подписок

    public void addSubscription(UserProfile subscriber) {
        this.subscribes.add(subscriber);
    }


}
