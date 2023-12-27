package org.education.network.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
import org.education.network.model.profile.UserProfile;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "fileList")
@Builder
@Entity
public class Post {

    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String title;
    private String description;
    private String location;

    @ManyToOne
    @JoinColumn(name = "profile_id",
            nullable = false)
    private UserProfile userProfile;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<File> fileList;

}
