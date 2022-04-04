package com.epam.esm.repository.specification;

import com.epam.esm.model.entity.Certificate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@RequiredArgsConstructor
public class CertificatesByNamePartSpecification implements Specification<Certificate> {

    private final String namePart;

    @Override
    public Predicate toPredicate(Root<Certificate> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        String formattedNamePart = String.format("%%%s%%", namePart);
        return criteriaBuilder.like(root.get("name"), formattedNamePart);
    }

}
