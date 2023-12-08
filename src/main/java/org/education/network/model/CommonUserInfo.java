package org.education.network.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
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
@ToString(exclude = {"university", "language"})
@Entity
public class CommonUserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String avatar;
    private String backPhoto;
    @Column(unique = true, nullable = false)
    private String phoneNumber;
    @OneToOne(mappedBy = "commonUserInfo")
    @JoinColumn(unique = true)
    private User user;
    //todo Посмотреть про зависимые сущности зависимых сущностей в ManyToMany при cascadetype = remove
    @ManyToMany(cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    @JoinTable(joinColumns = @JoinColumn(name = "commonUserInfo_id"))
    private List<University> university;
    @ManyToMany(cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    @JoinTable(name = "CommonUserInfo_language",
            joinColumns = @JoinColumn(name = "commonUserInfo_id"))
    private List<Language> language;
}
