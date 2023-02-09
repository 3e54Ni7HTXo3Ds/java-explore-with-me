package ru.practicum.ewm.category;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.category.dto.CatDto;
import ru.practicum.ewm.category.model.Cat;


@Component
public class CatMapper {

    public static CatDto toCatDto(Cat cat) {
        return new CatDto(
                cat.getId(),
                cat.getName()
        );
    }

    public static Cat toCat(CatDto catDto) {
        return new Cat(
                catDto.getId(),
                catDto.getName()
        );
    }
}