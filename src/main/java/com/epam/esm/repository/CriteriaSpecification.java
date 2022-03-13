package com.epam.esm.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * The interface Criteria specification is used to describe {@code CriteriaQuery} predicate.
 *
 * @param <T> the entity type
 */
public interface CriteriaSpecification<T> {

    /**
     * Returns custom {@code CriteriaQuery} predicate
     *
     * @param root            the root entity of query
     * @param criteriaBuilder the criteria builder
     * @return the constructed predicate
     */
    Predicate toPredicate(Root<T> root, CriteriaBuilder criteriaBuilder);
}
