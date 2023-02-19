package ru.practicum.ewm.user;

import org.springframework.data.domain.PageRequest;
import ru.practicum.ewm.error.exceptions.ConflictException;
import ru.practicum.ewm.user.dto.UserRequestDto;

import java.util.List;

public interface UserService {
    UserRequestDto create(UserRequestDto userRequestDto) throws ConflictException;

    void delete(Long userId);

    List<UserRequestDto> findUsersByIds(List<Long> ids, PageRequest pageRequest);
}
