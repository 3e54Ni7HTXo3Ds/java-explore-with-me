package ru.practicum.ewm.stats.service;

import ru.practicum.ewm.stats.dto.HitRequestDto;
import ru.practicum.ewm.stats.dto.HitResponseDto;

public interface StatsService {
    void create(HitRequestDto hitRequestDto);
}
