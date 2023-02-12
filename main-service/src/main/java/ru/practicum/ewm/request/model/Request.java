package ru.practicum.ewm.request.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @Column(name = "request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // — уникальный идентификатор

    @ManyToOne
    @JoinColumn(name = "request_event_initiator")
    private User initiator;// — пользователь инициатор события;

    @Column(name = "request_created")
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "request_event")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "request_requester")// — пользователь отправитель запроса на событие
    private User requester;

    @Column(name = "request_status")
    private String status;

}
