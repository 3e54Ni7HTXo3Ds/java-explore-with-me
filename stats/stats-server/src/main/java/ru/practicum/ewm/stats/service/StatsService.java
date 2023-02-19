package ru.practicum.ewm.stats.service;

import ru.practicum.ewm.stats.dto.HitRequestDto;
import ru.practicum.ewm.stats.dto.HitResponseDto;

import java.util.List;

public interface StatsService {
    void create(HitRequestDto hitRequestDto);

    List<HitResponseDto> get(String start, String end, List<String> uris, boolean unique);
}
