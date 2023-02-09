package ru.practicum.ewm.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.model.User;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User create(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        log.info("Добавлен новый пользователь: {} ", user);
        return userRepository.save(user);
    }

    @Override
    public void delete(Long userId) {
        if (userRepository.existsById(userId)) {
            log.info("Удаляем пользователя: {} ", userId);
            userRepository.deleteById(userId);
        } else {
            log.info("Нет пользователя: {} ", userId);
        }
    }

    @Override
    public Page<User> findUsersByIds(List<Long> ids, PageRequest pageRequest) {
        return userRepository.getUsersByIdIn(ids, pageRequest);
    }

}
