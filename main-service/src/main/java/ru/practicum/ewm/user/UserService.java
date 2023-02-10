package ru.practicum.ewm.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.practicum.ewm.error.exceptions.ConflictException;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.model.User;

import java.util.List;

public interface UserService {
    User create(UserDto userDto) throws ConflictException;

    void delete(Long userId);

    Page<User> findUsersByIds(List<Long> ids, PageRequest pageRequest);
}
