package ru.practicum.ewm.event.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.category.model.Cat;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {

    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // — уникальный идентификатор ;

    @Column(name = "event_title")
    private String eventTitle;//

    @Column(name = "event_annotation")
    private String eventAnnotation;//

    @Column(name = "event_description")
    private String eventDescription;//

    @ManyToOne
    @JoinColumn(name = "event_initiator")
    private User eventInitiator;//

    @Column(name = "event_created")
    private LocalDateTime eventCreated;//

    @ManyToOne
    @JoinColumn(name = "event_category")
    private Cat eventCat;//

    @Column(name = "event_date")
    private LocalDateTime eventDate;//

    @Column(name = "event_paid")
    private Boolean eventPaid;//

    @Column(name = "event_published_on")
    private LocalDateTime eventPublishedOn;//

    @Column(name = "event_participant_limit")
    private Long eventLimit;//

    @Column(name = "event_request_moderation")
    private Boolean eventRequestModeration;//

    @Column(name = "event_confirmed_requests")
    private Long eventConfirmedRequests;//

    @Column(name = "event_state")
    private String eventState;//

    @Column(name = "event_views")
    private Long eventViews;//

    @ManyToOne
    @JoinColumn(name = "event_location")
    private Location eventLocation;

    public Event(String eventTitle, String eventAnnotation, String eventDescription, User eventInitiator,
                 LocalDateTime eventCreated,
                 Cat eventCat, LocalDateTime eventDate, boolean eventPaid, Long eventLimit,
                 Boolean eventRequestModeration,
                 String eventState,
                 Long eventViews, Location eventLocation) {

        this.eventTitle = eventTitle;
        this.eventAnnotation = eventAnnotation;
        this.eventDescription = eventDescription;
        this.eventInitiator = eventInitiator;
        this.eventCreated = eventCreated;
        this.eventCat = eventCat;
        this.eventDate = eventDate;
        this.eventPaid = eventPaid;
        this.eventLimit = eventLimit;
        this.eventRequestModeration = eventRequestModeration;
        this.eventState = eventState;
        this.eventViews = eventViews;
        this.eventLocation = eventLocation;

    }
}
