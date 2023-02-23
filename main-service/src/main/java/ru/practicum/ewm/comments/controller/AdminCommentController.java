package ru.practicum.ewm.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comments.CommentService;
import ru.practicum.ewm.comments.dto.CommentRequestDto;
import ru.practicum.ewm.comments.dto.CommentResponseDto;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated

@RequestMapping("/admin/comments")
public class AdminCommentController {

    private final CommentService commentService;

    @DeleteMapping("/{commentId}")
    public void deleteAdminComment(@PathVariable Long commentId) throws NotFoundParameterException {
        commentService.deleteAdminComment(commentId);
    }

    @PatchMapping(path = "/{commentId}")
    public CommentResponseDto updateAdminComment(@Positive @PathVariable Long commentId,
                                                 @Valid @RequestBody CommentRequestDto commentRequestDto)
            throws NotFoundParameterException {
        return commentService.updateAdminComment(commentId, commentRequestDto);
    }

    @GetMapping
    public List<CommentResponseDto> getAdminComments(
            @RequestParam(required = false, defaultValue = "") String text,
            @PositiveOrZero @RequestParam(required = false, defaultValue = "0") int from,
            @Positive @RequestParam(required = false, defaultValue = "10") int size) {
        return commentService.getAdminComments(text, from, size);
    }
}
