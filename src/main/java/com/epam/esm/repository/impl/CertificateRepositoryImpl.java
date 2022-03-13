package com.epam.esm.repository.impl;

import com.epam.esm.repository.CriteriaSpecification;
import com.epam.esm.repository.api.CertificateRepository;
import com.epam.esm.model.entity.Certificate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository<Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Certificate> findAll(CriteriaSpecification<Certificate> criteriaSpecification, Integer pageNum,
                                     Integer pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> criteriaQuery = criteriaBuilder.createQuery(Certificate.class);
        Root<Certificate> root = criteriaQuery.from(Certificate.class);
        Predicate selectPredicate = criteriaSpecification.toPredicate(root, criteriaBuilder);
        criteriaQuery.select(root).where(selectPredicate);
        TypedQuery<Certificate> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult((pageNum - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Optional<Certificate> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Certificate.class, id));
    }

    @Override
    @Transactional
    public Certificate create(Certificate certificate) {
        entityManager.persist(certificate);
        return certificate;
    }

    @Override
    @Transactional
    public Certificate update(Long updateId, Certificate replacement) {
        Certificate certificate = entityManager.find(Certificate.class, updateId);
        entityManager.detach(certificate);
        certificate.setName(replacement.getName());
        certificate.setDescription(replacement.getDescription());
        certificate.setDuration(replacement.getDuration());
        certificate.setPrice(replacement.getPrice());
        if (replacement.getTags() != null) {
            certificate.setTags(replacement.getTags());
        }
        entityManager.merge(certificate);
        return certificate;
    }

    @Override
    @Transactional
    public Certificate updatePrice(Long updateId, Double price) {
        Certificate certificate = entityManager.find(Certificate.class, updateId);
        entityManager.detach(certificate);
        certificate.setPrice(price);
        entityManager.merge(certificate);
        return certificate;
    }

    @Override
    @Transactional
    public Certificate delete(Long deleteId) {
        Certificate certificate = entityManager.find(Certificate.class, deleteId);
        entityManager.remove(certificate);
        return certificate;
    }

    @Override
    public Long countCertificates() {
        return (Long) entityManager.createQuery("select count(id) from Certificate").getSingleResult();
    }
}
