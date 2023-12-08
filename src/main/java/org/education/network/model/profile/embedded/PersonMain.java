package org.education.network.model.profile.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Embeddable
public class PersonMain {

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastname;
    //@Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false)
    private boolean gender;

}
