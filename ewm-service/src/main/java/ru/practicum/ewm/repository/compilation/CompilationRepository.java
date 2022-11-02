package ru.practicum.ewm.repository.compilation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.compilation.Compilation;

/**
 * Class describing jpa compilation repository
 *
 * @author Timur Kiyamov
 * @version 1.0
 */
@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    /**
     * Method of repository which find all pinned compilations
     *
     * @param pinned
     * @param pageable
     * @return Pages of Compilation
     */
    Page<Compilation> findAllByPinned(Boolean pinned, Pageable pageable);
}