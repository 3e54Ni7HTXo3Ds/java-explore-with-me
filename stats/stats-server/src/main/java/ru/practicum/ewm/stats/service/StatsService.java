package ru.practicum.ewm.stats.service;

import ru.practicum.ewm.stats.dto.HitRequestDto;

public interface StatsService {
    void create(HitRequestDto hitRequestDto);
}
