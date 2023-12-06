package org.education.network.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "user_net")
public class User {

    @Id
    private UUID id = UUID.randomUUID();
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String password;
    @Column(unique = true)
    private String refreshToken;

}
