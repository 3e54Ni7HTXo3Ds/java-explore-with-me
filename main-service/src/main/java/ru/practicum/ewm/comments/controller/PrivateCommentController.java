package ru.practicum.ewm.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comments.CommentService;
import ru.practicum.ewm.comments.dto.CommentRequestDto;
import ru.practicum.ewm.comments.dto.CommentResponseDto;
import ru.practicum.ewm.error.exceptions.ConflictException;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping("/users/{userId}")
public class PrivateCommentController {

    private final CommentService commentService;

    @PostMapping(path = "/events/{eventId}/comments")
    public CommentResponseDto createUserComment(@Positive @PathVariable Long userId,
                                                @Positive @PathVariable Long eventId,
                                                @Valid @RequestBody CommentRequestDto commentRequestDto)
            throws NotFoundParameterException {
        return commentService.createUserComment(userId, eventId, commentRequestDto);
    }

    @GetMapping(path = "/comments")
    public List<CommentResponseDto> getUserComments(@Positive @PathVariable Long userId,
                                                    @RequestParam(required = false, defaultValue = "0") int from,
                                                    @RequestParam(required = false, defaultValue = "10") int size)
            throws NotFoundParameterException {
        return commentService.getUserComments(userId, from, size);
    }

    @DeleteMapping(path = "/comments/{commentId}")
    public void deleteUserComment(@Positive @PathVariable Long userId,
                                  @Positive @PathVariable Long commentId)
            throws NotFoundParameterException, ConflictException {
        commentService.deleteUserComment(userId, commentId);
    }

    @PatchMapping(path = "/comments/{commentId}")
    public CommentResponseDto updateUserComment(@Positive @PathVariable Long userId,
                                                @Positive @PathVariable Long commentId,
                                                @Valid @RequestBody CommentRequestDto commentRequestDto)
            throws NotFoundParameterException, ConflictException {
        return commentService.updateUserComment(userId, commentId, commentRequestDto);
    }
}



