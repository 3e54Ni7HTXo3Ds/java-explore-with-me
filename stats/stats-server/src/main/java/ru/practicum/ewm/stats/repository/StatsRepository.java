package ru.practicum.ewm.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.stats.dto.HitResponseDto;
import ru.practicum.ewm.stats.model.Hit;

import java.time.LocalDateTime;
import java.util.List;


public interface StatsRepository extends JpaRepository<Hit, Long> {

    @Query("select new ru.practicum.ewm.stats.dto.HitResponseDto(h.app, h.uri, count (h.ip)) " +
            "from Hit as h " +
            "where h.timestamp between ?1 AND ?2 " +
            "and h.uri in ?3 " +
            "group by  h.app, h.uri " +
            "order by count (h.ip) desc")
    List<HitResponseDto> findHitsByDate(LocalDateTime startTime, LocalDateTime endTime, List<String> uris);

}
