package com.epam.esm.repository.specification;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.CriteriaSpecification;
import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CertificatesBySeveralTagsSpecification implements CriteriaSpecification<Certificate> {

    private final List<Tag> tags;

    @Override
    public Predicate toPredicate(Root<Certificate> root, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        for (Tag t : tags) {
            Predicate p = criteriaBuilder.isMember(t, root.get("tags"));
            predicates.add(p);
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

}
