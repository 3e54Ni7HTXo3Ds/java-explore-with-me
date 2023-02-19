package ru.practicum.ewm.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.stats.dto.HitResponseDto;
import ru.practicum.ewm.stats.model.Hit;

import java.time.LocalDateTime;
import java.util.List;


public interface StatsRepository extends JpaRepository<Hit, Long> {

    @Query("select new ru.practicum.ewm.stats.dto.HitResponseDto(h.app, h.uri, count (h.ip)) " +
            "from Hit as h " +
            "where h.timestamp between :startTime AND :endTime " +
            "AND h.uri in :uris " +
            "group by  h.app, h.uri " +
            "order by count (h.ip) desc")
    List<HitResponseDto> findAllHitsByUriIn(@Param("startTime") LocalDateTime startTime,
                                            @Param("endTime") LocalDateTime endTime,
                                            @Param("uris") List<String> uris);

    @Query("select new ru.practicum.ewm.stats.dto.HitResponseDto(h.app, h.uri, count (distinct h.ip)) " +
            "from Hit as h " +
            "where h.timestamp between :startTime AND :endTime " +
            "AND h.uri in :uris " +
            "group by  h.app, h.uri " +
            "order by count (h.ip) desc")
    List<HitResponseDto> findAllUniqueHitsByUriIn(@Param("startTime") LocalDateTime startTime,
                                                  @Param("endTime") LocalDateTime endTime,
                                                  @Param("uris") List<String> uris);

    @Query("select new ru.practicum.ewm.stats.dto.HitResponseDto(h.app, h.uri, count (h.ip)) " +
            "from Hit as h " +
            "where h.timestamp between :startTime AND :endTime " +
            "group by  h.app, h.uri " +
            "order by count (h.ip) desc")
    List<HitResponseDto> findAllHits(@Param("startTime") LocalDateTime startTime,
                                     @Param("endTime") LocalDateTime endTime);

    @Query("select new ru.practicum.ewm.stats.dto.HitResponseDto(h.app, h.uri, count (distinct h.ip)) " +
            "from Hit as h " +
            "where h.timestamp between :startTime AND :endTime " +
            "group by  h.app, h.uri " +
            "order by count (h.ip) desc")
    List<HitResponseDto> findAllUniqueHits(@Param("startTime") LocalDateTime startTime,
                                           @Param("endTime") LocalDateTime endTime);

}
