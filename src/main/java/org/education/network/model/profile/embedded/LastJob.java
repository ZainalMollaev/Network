package org.education.network.model.profile.embedded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
