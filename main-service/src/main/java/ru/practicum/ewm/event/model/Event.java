package ru.practicum.ewm.event.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @Column(name = "event_category")
    private Long eventCat;//

    @Column(name = "event_date")
    private LocalDateTime eventData;//

    @Column(name = "event_paid")
    private Boolean eventPaid;//

    @Column(name = "event_published_on")
    private LocalDateTime eventPublishedOn;//

    @Column(name = "event_participant_limit")
    private Long eventTitle;//

    @Column(name = "event_request_moderation")
    private String eventTitle;//

    @Column(name = "event_confirmed_requests")
    private String eventTitle;//

    @Column(name = "event_state")
    private String eventTitle;//

    @Column(name = "event_views")
    private String eventTitle;//


}
