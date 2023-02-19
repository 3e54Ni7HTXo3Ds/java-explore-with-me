package ru.practicum.ewm.user;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.user.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> getUsersByIdIn(List<Long> ids, PageRequest pageRequest);

    Boolean existsByName(String name);
}
