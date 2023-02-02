package ru.practicum.ewm.stats.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stats")
public class Hit {
    @Id
    @Column(name = "stats_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //— уникальный идентификатор;
    @Column(name = "stats_timestamp")
    private LocalDateTime timestamp;  //— время;
    @Column(name = "stats_ip")
    private String ip; //— ip;
    @Column(name = "stats_app")
    private String app; //— приложение;
    @Column(name = "stats_uri")
    private String uri; //— приложение;

    public Hit(String app, String uri, String ip, LocalDateTime timestamp) {

        this.timestamp = timestamp;
        this.ip = ip;
        this.app = app;
        this.uri = uri;
    }
}


