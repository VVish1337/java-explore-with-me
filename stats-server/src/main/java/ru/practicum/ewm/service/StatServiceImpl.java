package ru.practicum.ewm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.mapper.StatMapper;
import ru.practicum.ewm.model.ViewStat;
import ru.practicum.ewm.repository.StatRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * Class which describes service of statistic
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Service
public class StatServiceImpl implements StatService {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final StatRepository repository;

    @Autowired
    public StatServiceImpl(StatRepository repository) {
        this.repository = repository;
    }

    /**
     * Method of service which save statistic
     *
     * @param hitDto
     */
    public void saveHit(HitDto hitDto) {
        Objects.requireNonNull(hitDto);
        repository.save(StatMapper.toModel(hitDto));
    }

    /**
     * Method of service which get statistic
     *
     * @param start
     * @param end
     * @param uris
     * @param unique
     * @return List of ViewStat
     */
    public List<ViewStat> getStats(String start, String end, List<String> uris, Boolean unique) {
        LocalDateTime startTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endTime = LocalDateTime.parse(end, formatter);
        if (unique) {
            return repository.findAllViewsUnique(startTime, endTime, uris);
        } else {
            return repository.findAllViews(startTime, endTime, uris);
        }
    }
}
