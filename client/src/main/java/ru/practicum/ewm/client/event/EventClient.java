package ru.practicum.ewm.client.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.client.base.BaseClient;
import ru.practicum.ewm.client.dto.HitDto;

@Slf4j
@Service
public class EventClient extends BaseClient {
    @Autowired
    public EventClient(@Value("${stat-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    public void createHit(HitDto hitDto) {
        log.info("post hit dto:{}",hitDto);
        post("/hit", hitDto);
    }
}