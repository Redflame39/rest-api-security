package com.epam.esm.repository.specification;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.repository.CriteriaSpecification;
import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CertificatesQuerySpecification implements CriteriaSpecification<Certificate> {

    private final List<CriteriaSpecification<Certificate>> criteriaSpecifications;

    @Override
    public Predicate toPredicate(Root<Certificate> root, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicatesOfSpecifications = criteriaSpecifications.stream()
                .map(s -> s.toPredicate(root, criteriaBuilder))
                .collect(Collectors.toList());
        return criteriaBuilder.and(predicatesOfSpecifications.toArray(new Predicate[0]));
    }

}
