package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.CatService;
import ru.practicum.ewm.category.dto.CatDto;
import ru.practicum.ewm.error.exceptions.ConflictException;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCatController {

    private final CatService catService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CatDto create(@Valid @RequestBody CatDto catDto) throws ConflictException {
        return (catService.create(catDto));
    }

    @PatchMapping("/{id}")
    public CatDto update(@Valid @RequestBody CatDto catDto,
                         @Positive @PathVariable Long id) throws ConflictException {
        return catService.update(id, catDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws ConflictException {
        catService.delete(id);
    }

}
