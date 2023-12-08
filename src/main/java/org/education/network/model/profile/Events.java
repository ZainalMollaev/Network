package org.education.network.model.profile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@ToString(exclude = {"eventTime", "worthEvents"})
@Entity
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "events_eventTime",
            joinColumns = @JoinColumn(name = "events_id"))
    private List<EventTime> eventTime;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "events_worthEvents",
            joinColumns = @JoinColumn(name = "events_id"))
    private List<WorthEvents> worthEvents;
    @ManyToOne(optional = false)
    @JoinColumn(name = "events_id", nullable = false)
    private Events events;
    private boolean isDiscussion;
    private boolean isHidePart;
    private boolean isWeatherOn;
}