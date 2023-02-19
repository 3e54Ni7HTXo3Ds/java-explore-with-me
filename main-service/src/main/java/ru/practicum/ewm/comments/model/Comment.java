package ru.practicum.ewm.comments.model;

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
@Table(name = "comments")
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // — уникальный идентификатор ;

    @Column(name = "comment_text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "comment_user")
    private User commentator;//

    @ManyToOne
    @JoinColumn(name = "comment_event")
    private Event event;//

    @Column(name = "comment_date")
    private LocalDateTime date;//

}



