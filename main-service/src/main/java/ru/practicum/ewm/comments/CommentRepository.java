package ru.practicum.ewm.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.comments.model.Comment;
import ru.practicum.ewm.event.OffsetBasedPageRequest;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByCommentatorIdOrderByDateDesc(Long userId, OffsetBasedPageRequest offsetBasedPageRequest);
    List<Comment> findAllByEventIdOrderByDateDesc(Long eventId, OffsetBasedPageRequest offsetBasedPageRequest);

    List<Comment> findAllByTextContainingOrderByDateDesc(String text, OffsetBasedPageRequest offsetBasedPageRequest);
}
