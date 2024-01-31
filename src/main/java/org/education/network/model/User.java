package org.education.network.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.education.network.enumtypes.Roles;
import org.education.network.model.profile.UserProfile;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "users")
public class User {

    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String password;
    @Column(unique = true)
    private String refreshToken;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Roles role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserProfile userProfile;

}
