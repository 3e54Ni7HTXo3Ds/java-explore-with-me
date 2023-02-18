package ru.practicum.ewm.stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.dto.HitRequestDto;
import ru.practicum.ewm.stats.dto.HitResponseDto;
import ru.practicum.ewm.stats.mapper.HitMapper;
import ru.practicum.ewm.stats.repository.StatsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    @Override
    public void create(HitRequestDto hitRequestDto) {
        statsRepository.save(HitMapper.toHit(hitRequestDto));
    }

    @Override
    public List<HitResponseDto> get(String start, String end, List<String> urisInc, boolean unique) {
        List<String> uris = new ArrayList<>();
        if (urisInc != null) {
            for (String u : urisInc) {
                uris.add(u.replaceAll("[]\\[]", ""));
            }
        }

        LocalDateTime startTime = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endTime = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        if (unique) {
            if (uris.isEmpty()) {
                return statsRepository.findAllUniqueHits(startTime, endTime);
            }
            return statsRepository.findAllUniqueHitsByUriIn(startTime, endTime, uris);
        } else {
            if (uris.isEmpty()) {
                return statsRepository.findAllHits(startTime, endTime);
            }
            return statsRepository.findAllHitsByUriIn(startTime, endTime, uris);
        }
    }
}
