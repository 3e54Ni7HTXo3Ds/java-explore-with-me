package ru.practicum.ewm.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.error.exceptions.ConflictException;
import ru.practicum.ewm.user.dto.UserRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.user.UserMapper.toUserRequestDto;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserRequestDto> get(@RequestParam List<Long> ids,
                                    @PositiveOrZero @RequestParam(required = false, defaultValue = "0") int from,
                                    @Positive @RequestParam(required = false, defaultValue = "10") int size) {

        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by("id").ascending());
        return userService.findUsersByIds(ids, pageRequest).stream()
                .map(UserMapper::toUserRequestDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserRequestDto create(@Valid @RequestBody UserRequestDto userRequestDto) throws ConflictException {
        return toUserRequestDto(userService.create(userRequestDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @PathVariable("id") Long userId) {
        userService.delete(userId);
    }
}
