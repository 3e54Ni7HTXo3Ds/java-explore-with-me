package ru.practicum.ewm.user;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.dto.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@Slf4j
@Data
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> get(@RequestParam List<Long> ids,
                             @PositiveOrZero @RequestParam(required = false, defaultValue = "0") int from,
                             @Positive @RequestParam(required = false, defaultValue = "10") int size) {

        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by("id").ascending());
        return userService.findUsersByIds(ids, pageRequest).stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        log.info("Create new user, userDto={}.", userDto);
        return UserMapper.toUserDto(userService.create(userDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @PathVariable("id") Long userId) {
        userService.delete(userId);
    }
}
