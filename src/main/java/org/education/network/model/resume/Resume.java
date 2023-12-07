package org.education.network.model.resume;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(joinColumns = @JoinColumn(name = "resume_id"))
    private List<Job> jobs;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Resume_volunteers",
            joinColumns = @JoinColumn(name = "resume_id"))
    private List<Volunteer> volunteers;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Resume_courses",
            joinColumns = @JoinColumn(name = "resume_id"))
    private List<Course> courses;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Resume_certificates",
            joinColumns = @JoinColumn(name = "resume_id"))
    private List<Certificate> certificates;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Resume_patents",
            joinColumns = @JoinColumn(name = "resume_id"))
    private List<Patent> patents;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Resume_recomenadations",
            joinColumns = @JoinColumn(name = "resume_id"))
    private List<Recomenadation> recomenadations;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Resume_skills",
            joinColumns = @JoinColumn(name = "resume_id"))
    private List<Skill> skills;
}
