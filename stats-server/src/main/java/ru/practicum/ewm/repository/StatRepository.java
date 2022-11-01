package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.Hit;
import ru.practicum.ewm.model.ViewStat;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatRepository extends JpaRepository<Hit, Long> {
    @Query("select new ru.practicum.ewm.stat.model.ViewStats(" +
            "a.app,a.uri, count(distinct a.ip)) " +
            "from Hit a " +
            "where a.timestamp between ?1 and ?2 " +
            "and a.uri in (?3) " +
            "group by a.app, a.uri ")
    List<ViewStat> findAllViewsUnique(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select new ru.practicum.ewm.stat.model.ViewStats(" +
            "a.app,a.uri, count(a.ip)) " +
            "from Hit a " +
            "where a.timestamp between ?1 and ?2 " +
            "and a.uri in (?3) " +
            "group by a.app, a.uri ")
    List<ViewStat> findAllViews(LocalDateTime start, LocalDateTime end, List<String> uris);
}