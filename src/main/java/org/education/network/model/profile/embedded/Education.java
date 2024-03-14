package org.education.network.model.profile.embedded;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.education.network.enumtypes.Privacy;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Embeddable
public class Education {

    private String specialization;
    private String university;
    @Enumerated(EnumType.STRING)
    private Privacy educationPrivacy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Education education = (Education) o;
        return Objects.equals(specialization, education.specialization) && Objects.equals(university, education.university);
    }

    @Override
    public int hashCode() {
        return Objects.hash(specialization, university);
    }
}
