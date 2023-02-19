package ru.practicum.ewm.comments;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.comments.dto.CommentRequestDto;
import ru.practicum.ewm.comments.dto.CommentResponseDto;
import ru.practicum.ewm.comments.model.Comment;
import ru.practicum.ewm.error.exceptions.ConflictException;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;
import ru.practicum.ewm.event.EventRepository;
import ru.practicum.ewm.event.OffsetBasedPageRequest;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.UserRepository;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.practicum.ewm.comments.CommentMapper.toComment;
import static ru.practicum.ewm.comments.CommentMapper.toCommentResponseDto;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public CommentResponseDto createUserComment(Long userId, Long eventId, CommentRequestDto commentRequestDto)
            throws NotFoundParameterException {
        User commentator =
                userRepository.findById(userId).orElseThrow(() -> new NotFoundParameterException("Wrong user"));
        Event event =
                eventRepository.findById(eventId).orElseThrow(() -> new NotFoundParameterException("Wrong event"));
        Comment comment = toComment(commentRequestDto, commentator, event);
        return toCommentResponseDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentResponseDto> getUserComments(Long userId, int from, int size) throws NotFoundParameterException {
        OffsetBasedPageRequest offsetBasedPageRequest = new OffsetBasedPageRequest(from, size);
        userRepository.findById(userId).orElseThrow(() -> new NotFoundParameterException("Wrong user"));

        return commentRepository.findAllByCommentatorIdOrderByDateDesc(userId, offsetBasedPageRequest).stream()
                .map(CommentMapper::toCommentResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserComment(Long userId, Long commentId) throws NotFoundParameterException, ConflictException {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundParameterException("Wrong comment"));
        userRepository.findById(userId).orElseThrow(() -> new NotFoundParameterException("Wrong user"));
        if (!Objects.equals(comment.getCommentator().getId(), userId)) {
            throw new ConflictException("Its not your comment");
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentResponseDto updateUserComment(Long userId, Long commentId, CommentRequestDto commentRequestDto)
            throws NotFoundParameterException, ConflictException {

        userRepository.findById(userId).orElseThrow(() -> new NotFoundParameterException("Wrong user"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundParameterException("Wrong comment"));

        if (!Objects.equals(comment.getCommentator().getId(), userId)) {
            throw new ConflictException("Its not your comment");
        }
        comment.setText(commentRequestDto.getText());
        comment.setDate(LocalDateTime.now());
        return toCommentResponseDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentResponseDto> getEventComments(Long eventId, int from, int size)
            throws NotFoundParameterException {
        OffsetBasedPageRequest offsetBasedPageRequest = new OffsetBasedPageRequest(from, size);
        eventRepository.findById(eventId).orElseThrow(() -> new NotFoundParameterException("Wrong event"));
        return commentRepository.findAllByEventIdOrderByDateDesc(eventId, offsetBasedPageRequest).stream()
                .map(CommentMapper::toCommentResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponseDto getComment(Long commentId) throws NotFoundParameterException {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundParameterException("Wrong comment"));
        return toCommentResponseDto(comment);
    }

    @Override
    public void deleteAdminComment(Long commentId) throws NotFoundParameterException {
        commentRepository.findById(commentId).orElseThrow(() -> new NotFoundParameterException("Wrong comment"));
        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentResponseDto updateAdminComment(Long commentId, CommentRequestDto commentRequestDto)
            throws NotFoundParameterException {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundParameterException("Wrong comment"));
        comment.setText(commentRequestDto.getText());
        comment.setDate(LocalDateTime.now());
        return toCommentResponseDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentResponseDto> getAdminComments(String text, int from, int size) {
        OffsetBasedPageRequest offsetBasedPageRequest = new OffsetBasedPageRequest(from, size);
        List<Comment> list = commentRepository.findAllByTextContainingOrderByDateDesc(text, offsetBasedPageRequest);
        return list.stream()
                .map(CommentMapper::toCommentResponseDto)
                .collect(Collectors.toList());
    }
}
