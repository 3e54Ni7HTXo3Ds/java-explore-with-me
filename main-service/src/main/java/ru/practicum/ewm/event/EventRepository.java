package ru.practicum.ewm.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.category.model.Cat;
import ru.practicum.ewm.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByEventInitiatorId(Long userId, OffsetBasedPageRequest offsetBasedPageRequest);

    Boolean existsByEventCat(Cat cat);

    @Query("SELECT E FROM Event E " +
            "WHERE ((:users) IS NULL OR E.eventInitiator.id IN (:users)) " +
            "AND ((:states) IS NULL OR E.eventState IN (:states)) " +
            "AND ((:categories) IS NULL OR E.eventCat.id IN (:categories)) " +
            "AND (E.eventDate BETWEEN :startTime AND :endTime)")
    List<Event> findEventsAdmin(@Param("users") List<Long> users, @Param("states") List<String> states,
                                @Param("categories") List<Long> categories, @Param("startTime") LocalDateTime startTime,
                                @Param("endTime") LocalDateTime endTime, OffsetBasedPageRequest offsetBasedPageRequest);

    @Query("select e from Event e " +
            "where (:text is null OR (lower(e.eventAnnotation) like %:text% OR lower(e.eventDescription) like %:text% )) " +
            "AND ((:categories) is null OR e.eventCat.id IN (:categories)) " +
            "AND (:paid is null OR e.eventPaid = :paid) " +
            "AND (e.eventDate BETWEEN :startTime AND :endTime) " +
            "AND (e.eventState='PUBLISHED')")
    List<Event> findEventsPublic(@Param("text") String text, @Param("categories") List<Long> categories,
                                 @Param("paid") Boolean paid, @Param("startTime") LocalDateTime startTime,
                                 @Param("endTime") LocalDateTime endTime,
                                 OffsetBasedPageRequest offsetBasedPageRequest);
}
