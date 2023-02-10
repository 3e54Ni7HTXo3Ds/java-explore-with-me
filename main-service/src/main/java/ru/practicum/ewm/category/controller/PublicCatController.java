package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.CatMapper;
import ru.practicum.ewm.category.CatService;
import ru.practicum.ewm.category.dto.CatDto;
import ru.practicum.ewm.error.exceptions.NotFoundParameterException;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.category.CatMapper.toCatDto;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicCatController {

    private final CatService catService;

    @GetMapping
    public List<CatDto> getCats(@PositiveOrZero @RequestParam(required = false, defaultValue = "0") int from,
                                @Positive @RequestParam(required = false, defaultValue = "10") int size) {

        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by("id").ascending());
        return catService.getCatsPageble(pageRequest).stream()
                .map(CatMapper::toCatDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "{id}")
    public CatDto getCatById(@Positive @PathVariable Long id) throws NotFoundParameterException {
        return toCatDto(catService.getCatById(id));
    }
}
