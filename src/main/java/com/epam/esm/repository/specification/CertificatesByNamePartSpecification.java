package com.epam.esm.repository.specification;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.repository.CriteriaSpecification;
import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@RequiredArgsConstructor
public class CertificatesByNamePartSpecification implements CriteriaSpecification<Certificate> {

    private final String namePart;

    @Override
    public Predicate toPredicate(Root<Certificate> root, CriteriaBuilder criteriaBuilder) {
        String formattedNamePart = String.format("%%%s%%", namePart);
        return criteriaBuilder.like(root.get("name"), formattedNamePart);
    }

}
