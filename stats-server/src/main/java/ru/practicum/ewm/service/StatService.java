package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.HitDto;
import ru.practicum.ewm.model.ViewStat;

import java.util.List;

/**
 * Interface which describes service of statistic
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
public interface StatService {
    /**
     * Method of service which save statistic
     *
     * @param hitDto
     */
    void saveHit(HitDto hitDto);

    /**
     * Method of service which get statistic
     *
     * @param start
     * @param end
     * @param uris
     * @param unique
     * @return List of ViewStat
     */
    List<ViewStat> getStats(String start, String end, List<String> uris, Boolean unique);
}
