package ru.practicum.ewm.stats.mapper;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.dto.HitRequestDto;
import ru.practicum.ewm.stats.model.Hit;

@Component
@AllArgsConstructor
public class HitMapper {

    public static Hit toHit(HitRequestDto hitRequestDto) {
        return new Hit(
                hitRequestDto.getApp(),
                hitRequestDto.getUri(),
                hitRequestDto.getIp(),
                hitRequestDto.getTimestamp()
        );
    }

}
