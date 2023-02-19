package ru.practicum.ewm.location.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "location")
public class Location {

    @Id
    @Column(name = "location_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // — уникальный идентификатор ;

    @Column(name = "lat")
    private Float lat;

    @Column(name = "lon")
    private Float lon;

    public Location(float lat, float lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
