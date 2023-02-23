package ru.practicum.ewm.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comments.CommentService;
import ru.practicum.ewm.comments.dto.CommentResponseDto;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated

@RequestMapping("/comments")
public class PublicCommentController {

    private final CommentService commentService;

    @GetMapping(path = "/events/{eventId}")
    public List<CommentResponseDto> getEventComments(
            @Positive @PathVariable Long eventId,
            @PositiveOrZero @RequestParam(required = false, defaultValue = "0") int from,
            @Positive @RequestParam(required = false, defaultValue = "10") int size)
            throws NotFoundParameterException {
        return commentService.getEventComments(eventId, from, size);
    }

    @GetMapping(path = "/{commentId}")
    public CommentResponseDto getComment(@Positive @PathVariable Long commentId)
            throws NotFoundParameterException {
        return commentService.getComment(commentId);
    }


}
