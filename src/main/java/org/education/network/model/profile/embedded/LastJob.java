package org.education.network.model.profile.embedded;

import jakarta.persistence.Column;
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
public class LastJob {

    private String title;
    private String company;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Privacy jobPrivacy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LastJob lastJob = (LastJob) o;
        return Objects.equals(title, lastJob.title) && Objects.equals(company, lastJob.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, company);
    }
}
