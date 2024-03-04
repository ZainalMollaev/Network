package org.education.network.model.profile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.education.network.model.Role;
import org.education.network.model.User;
import org.education.network.model.post.Post;
import org.education.network.model.profile.embedded.Education;
import org.education.network.model.profile.embedded.LastJob;
import org.education.network.model.profile.embedded.PersonMain;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@NamedEntityGraph(
        name = "userProfilePosts",
        attributeNodes = {
                @NamedAttributeNode(value = "subscribes", subgraph = "subgraph_posts")
        },
        subgraphs = {
                @NamedSubgraph(name = "subgraph_posts",
                attributeNodes = {
                        @NamedAttributeNode(value = "posts")
                })
        }
)
@NamedEntityGraph(
        name = "saveUserProfile",
        attributeNodes = {
                @NamedAttributeNode(value = "languages"),
                @NamedAttributeNode(value = "roles"),
                @NamedAttributeNode(value = "personMain"),
                @NamedAttributeNode(value = "lastjob"),
                @NamedAttributeNode(value = "education"),
                @NamedAttributeNode(value = "location"),
                @NamedAttributeNode(value = "phoneNumber")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"subscribes", "languages", "posts", "roles", "user"})
@Builder
@Entity
public class UserProfile {

    @Id
    @org.hibernate.annotations.UuidGenerator
    private UUID id;
    @Embedded
    private PersonMain personMain;
    @Embedded
    private LastJob lastjob;
    @Embedded
    private Education education;
    private String location;
    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userProfile", orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Builder.Default
    private Set<Post> posts = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_profile_language",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    @Builder.Default
    private Set<Language> languages = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name="tbl_subscribers",
            joinColumns=@JoinColumn(name="profile_id"),
            inverseJoinColumns=@JoinColumn(name="subscriber_id"),
            uniqueConstraints = {
                    @UniqueConstraint(columnNames = { "profile_id", "subscriber_id" })}
    )
    @Builder.Default
    private Set<UserProfile> subscribes = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_profile_roles",
                joinColumns = @JoinColumn(name = "profile_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"),
                uniqueConstraints = @UniqueConstraint(columnNames = {"profile_id","role_id"}))
    @Builder.Default
    private List<Role> roles = new ArrayList<>();

    //todo Реализовать количество подписчиков и количество подписок

    public void addSubscription(UserProfile subscriber) {
        this.subscribes.add(subscriber);
    }
}
