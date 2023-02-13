package ru.practicum.ewm.compilation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.event.model.Event;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "compilations")
public class Compilation {

    @Id
    @Column(name = "compilation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // — уникальный идентификатор ;

    @Column(name = "compilation_pinned")
    private Boolean pinned;//

    @Column(name = "compilation_title")
    private String title;//

    @ManyToMany
    @JoinTable(
            name = "compilations_events",
            joinColumns = @JoinColumn(name = "compilations_events_compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "compilations_events_event_id"))
    private List<Event> eventList;

}
