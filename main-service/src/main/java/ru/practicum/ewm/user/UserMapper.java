package ru.practicum.ewm.user;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.user.dto.UserRequestDto;
import ru.practicum.ewm.user.dto.UserResponseDto;
import ru.practicum.ewm.user.model.User;


@Component
public class UserMapper {

    public static UserRequestDto toUserRequestDto(User user) {
        return new UserRequestDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static UserResponseDto toUserResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName()
        );
    }

    public static User toUser(UserRequestDto userRequestDto) {
        return new User(
                userRequestDto.getId(),
                userRequestDto.getName(),
                userRequestDto.getEmail()
        );
    }
}