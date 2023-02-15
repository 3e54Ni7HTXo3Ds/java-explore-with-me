package ru.practicum.ewm.category;

import org.springframework.data.domain.PageRequest;
import ru.practicum.ewm.category.dto.CatDto;
import ru.practicum.ewm.error.exceptions.ConflictException;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;

import java.util.List;

public interface CatService {
    CatDto create(CatDto catDto) throws ConflictException;

    CatDto update(Long id, CatDto catDto) throws ConflictException;

    void delete(Long id) throws ConflictException;

    List<CatDto> getCatsPageble(PageRequest pageRequest);

    CatDto getCatById(Long id) throws NotFoundParameterException;
}
