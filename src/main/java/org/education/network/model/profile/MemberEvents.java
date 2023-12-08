package org.education.network.model.profile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class MemberEvents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userProfile_id", nullable = false)
    private UserProfile userProfile;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "events_id", nullable = false)
    private Events events;
    @Column(nullable = false)
    private boolean isCreator;
}
