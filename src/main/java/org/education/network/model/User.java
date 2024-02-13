package org.education.network.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import org.education.network.model.profile.UserProfile;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.uuid.UuidGenerator;

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
    @GenericGenerator(name = "UUID",
            type = UuidGenerator.class)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String password;
    @Column(unique = true)
    private String refreshToken;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @PrimaryKeyJoinColumn
    private UserProfile userProfile;

}
