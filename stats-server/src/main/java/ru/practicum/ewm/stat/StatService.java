package ru.practicum.ewm.stat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stat.controller.StatMapper;
import ru.practicum.ewm.stat.controller.StatRepository;
import ru.practicum.ewm.stat.dto.HitDto;
import ru.practicum.ewm.stat.model.ViewStats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class StatService {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final StatRepository repository;

    @Autowired
    public StatService(StatRepository repository) {
        this.repository = repository;
    }

    public void saveHit(HitDto hitDto) {
        repository.save(StatMapper.toModel(hitDto));
    }

    public List<ViewStats> getStats(String start, String end, List<String> uris, Boolean unique) {
        LocalDateTime startTime = LocalDateTime.parse(start,formatter);
        LocalDateTime endTime = LocalDateTime.parse(end,formatter);
        if (unique) {
            return repository.findAllViewsUnique(startTime, endTime, uris);
        } else {
            return repository.findAllViews(startTime, endTime, uris);
        }
    }
}
