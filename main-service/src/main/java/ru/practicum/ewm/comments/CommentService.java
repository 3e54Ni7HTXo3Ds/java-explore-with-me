package ru.practicum.ewm.comments;

import ru.practicum.ewm.comments.dto.CommentRequestDto;
import ru.practicum.ewm.comments.dto.CommentResponseDto;
import ru.practicum.ewm.error.exceptions.ConflictException;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;

import java.util.List;

public interface CommentService {
    CommentResponseDto createUserComment(Long userId, Long eventId, CommentRequestDto commentRequestDto)
            throws NotFoundParameterException;

    List<CommentResponseDto> getUserComments(Long userId, int from, int size) throws NotFoundParameterException;

    void deleteUserComment(Long userId, Long commentId) throws NotFoundParameterException, ConflictException;

    CommentResponseDto updateUserComment(Long userId, Long commentId, CommentRequestDto commentRequestDto)
            throws NotFoundParameterException, ConflictException;

    List<CommentResponseDto> getEventComments(Long eventId, int from, int size) throws NotFoundParameterException;

    CommentResponseDto getComment(Long commentId) throws NotFoundParameterException;

    void deleteAdminComment(Long commentId) throws NotFoundParameterException;

    CommentResponseDto updateAdminComment(Long commentId, CommentRequestDto commentRequestDto)
            throws NotFoundParameterException;

    List<CommentResponseDto> getAdminComments(String text, int from, int size);
}
