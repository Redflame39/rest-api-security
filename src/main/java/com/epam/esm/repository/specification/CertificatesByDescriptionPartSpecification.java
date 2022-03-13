package com.epam.esm.repository.specification;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.repository.CriteriaSpecification;
import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@RequiredArgsConstructor
public class CertificatesByDescriptionPartSpecification implements CriteriaSpecification<Certificate> {

    private final String descriptionPart;

    @Override
    public Predicate toPredicate(Root<Certificate> root, CriteriaBuilder criteriaBuilder) {
        String formattedDescriptionPart = String.format("%%%s%%", descriptionPart);
        return criteriaBuilder.like(root.get("description"), formattedDescriptionPart);
    }

}
