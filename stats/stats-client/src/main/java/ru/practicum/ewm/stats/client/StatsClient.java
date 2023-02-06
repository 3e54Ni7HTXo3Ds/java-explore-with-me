package ru.practicum.ewm.stats.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.stats.dto.HitRequestDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class StatsClient extends BaseClient {

    @Autowired
    public StatsClient(@Value("${ewm.statsclient.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> createHit(HttpServletRequest httpServletRequest) {
        return post("/hit",
                new HitRequestDto(
                        "ewm-main-service",
                        httpServletRequest.getRequestURI(),
                        httpServletRequest.getRemoteAddr(),
                        LocalDateTime.now()));
    }

    public ResponseEntity<Object> getHits(String start, String end, String uris, Boolean unique) {
        return get("stats?start={start}&end={end}&uris={uris}&unique={unique}", Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", unique
        ));
    }
}
