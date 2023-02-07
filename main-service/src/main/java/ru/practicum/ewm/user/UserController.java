package ru.practicum.ewm.user;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.dto.UserDto;

import javax.validation.Valid;


@RestController
@Slf4j
@Data
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;


    @GetMapping("/{id}")
    public UserDto get(@PathVariable Long id) {
      //  return userService.findById(id);
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody UserDto userDto) {
       // return UserMapper.toUserDto(userService.create(userDto));
        return userDto;
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long userId) {
      //  userService.delete(userId);
    }
}
