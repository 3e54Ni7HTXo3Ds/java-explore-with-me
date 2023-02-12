package ru.practicum.ewm.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.error.exceptions.ConflictException;
import ru.practicum.ewm.user.dto.UserRequestDto;
import ru.practicum.ewm.user.model.User;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User create(UserRequestDto userRequestDto) throws ConflictException {
        User user = UserMapper.toUser(userRequestDto);
        if (userRepository.existsByName(user.getName())) {
            throw new ConflictException("Name exists");
        }
        log.info("User adding: {} ", user);
        return userRepository.save(user);
    }

    @Override
    public void delete(Long userId) {
        if (userRepository.existsById(userId)) {
            log.info("Deleting user: {} ", userId);
            userRepository.deleteById(userId);
        } else {
            log.info("No user: {} ", userId);
        }
    }

    @Override
    public List<User> findUsersByIds(List<Long> ids, PageRequest pageRequest) {
        return userRepository.getUsersByIdIn(ids, pageRequest);
    }

}
