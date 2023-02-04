package ru.practicum.ewm.stats.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.stats.dto.HitRequestDto;
import ru.practicum.ewm.stats.dto.HitResponseDto;
import ru.practicum.ewm.stats.service.StatsService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Component
@Slf4j
@Data
@RequestMapping(path = "")
public class StatsController {

    private final StatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody HitRequestDto hitRequestDto) {
        log.info("Creating hit in stats service");
        statsService.create(hitRequestDto);
    }

    @GetMapping("/stats")
    public List<HitResponseDto> getHits(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(required = false, defaultValue = "false") boolean unique) {
        log.info("Getting hits from stats service with start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        return statsService.get(start, end, uris, unique);
    }
}
