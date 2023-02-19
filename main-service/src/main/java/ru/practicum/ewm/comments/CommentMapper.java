package ru.practicum.ewm.comments;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.comments.dto.CommentRequestDto;
import ru.practicum.ewm.comments.dto.CommentResponseDto;
import ru.practicum.ewm.comments.model.Comment;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CommentMapper {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Comment toComment(CommentRequestDto commentRequestDto, User user, Event event) {
        return new Comment(
                null,
                commentRequestDto.getText(),
                user,
                event,
                LocalDateTime.now()
        );
    }

    public static CommentResponseDto toCommentResponseDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getText(),
                comment.getCommentator().getId(),
                comment.getEvent().getId(),
                dateTimeFormatter.format(comment.getDate())
        );
    }
}
