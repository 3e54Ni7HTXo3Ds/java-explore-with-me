package ru.practicum.ewm.user;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.dto.UserResponseDto;
import ru.practicum.ewm.user.model.User;


@Component
public class UserMapper {

    public static UserDto toUserDto(User user) {
        return new UserDto(
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

    public static User toUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail()
        );
    }
}