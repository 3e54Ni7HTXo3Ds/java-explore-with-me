package ru.practicum.ewm.category;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.category.model.Cat;

public interface CatRepository extends JpaRepository<Cat, Long> {
    Boolean existsByName(String catName);

}
