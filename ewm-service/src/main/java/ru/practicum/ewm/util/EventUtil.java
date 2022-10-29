package ru.practicum.ewm.util;

import org.springframework.data.jpa.domain.Specification;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventFilterParams;
import ru.practicum.ewm.event.model.PublicationState;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventUtil {
    public static Specification<Event> getSpecification(EventFilterParams params, boolean publicRequest) {
        return  (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (params.getUsers() != null) {
                for (Long userId : params.getUsers()) {
                    predicates.add(criteriaBuilder.in(root.get("initiator").get("id")).value(userId));
                }
            }
            if (publicRequest) {
                predicates.add(criteriaBuilder.equal(root.get("state"), PublicationState.PUBLISHED));
            } else {
                for (PublicationState state : params.getStates()) {
                    predicates.add(criteriaBuilder.in(root.get("state")).value(state));
                }
            }
            if (params.getText()!=null) {
                predicates.add(criteriaBuilder.like(root.get("annotation"), "%"+params.getText()+"%"));
                predicates.add(criteriaBuilder.like(root.get("description"), "%"+params.getText()+"%"));
            }
            if (null != params.getCategories() && !params.getCategories().isEmpty()){
                for (Long catId : params.getCategories()) {
                    predicates.add(criteriaBuilder.in(root.get("category").get("id")).value(catId));
                }
            }
            if (params.getPaid()!=null) {
                predicates.add(criteriaBuilder.equal(root.get("paid"), params.getPaid()));
            }
            if (null != params.getRangeStart()) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("publishedOn"), params.getRangeStart()));
            } else {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("publishedOn"), LocalDateTime.now()));
            }
            if (null != params.getRangeEnd()) {
                predicates.add(criteriaBuilder.lessThan(root.get("publishedOn"), params.getRangeEnd()));
            } else {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("publishedOn"), LocalDateTime.now()));
            }
            if (null != params.getOnlyAvailable() && params.getOnlyAvailable()) {
                predicates.add(criteriaBuilder.lessThan(root.get("participantLimit"), root.get("confirmedRequests")));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
