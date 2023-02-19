package ru.practicum.ewm.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.category.model.Cat;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    Boolean existsByName(String catName);
}
