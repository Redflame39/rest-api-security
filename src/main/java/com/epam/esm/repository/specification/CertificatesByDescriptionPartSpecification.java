package com.epam.esm.repository.specification;

import com.epam.esm.model.entity.Certificate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@RequiredArgsConstructor
public class CertificatesByDescriptionPartSpecification implements Specification<Certificate> {

    private final String descriptionPart;

    @Override
    public Predicate toPredicate(Root<Certificate> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        String formattedDescriptionPart = String.format("%%%s%%", descriptionPart);
        return criteriaBuilder.like(root.get("description"), formattedDescriptionPart);
    }
}
