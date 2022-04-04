package com.epam.esm.repository.specification;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CertificatesBySeveralTagsSpecification implements Specification<Certificate> {

    private final List<Tag> tags;

    @Override
    public Predicate toPredicate(Root<Certificate> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        for (Tag t : tags) {
            Predicate p = criteriaBuilder.isMember(t, root.get("tags"));
            predicates.add(p);
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

}
