package com.epam.esm.repository.impl;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.repository.NativeSpecification;
import com.epam.esm.repository.api.TagRepository;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.entity.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.*;

@Repository
public class TagRepositoryImpl implements TagRepository<Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tag> findAll(Integer pageNum, Integer pageSize) {
        TypedQuery<Tag> query = entityManager.createQuery("from Tag", Tag.class);
        query.setFirstResult((pageNum - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Optional<Tag> findByName(String name) {
        TypedQuery<Tag> byNameQuery = entityManager.createQuery("from Tag where name = ?1", Tag.class);
        byNameQuery.setParameter(1, name);
        try {
            return Optional.of(byNameQuery.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Tag> findByCertificateId(Long id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = builder.createQuery(Tag.class);
        Root<Tag> tagRoot = criteriaQuery.from(Tag.class);
        Join<Tag, Certificate> join = tagRoot.join("gift_certificates");
        criteriaQuery.select(tagRoot);
        criteriaQuery.where(builder.equal(join.get("id"), id));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    @Transactional
    public Tag create(TagDto tag) {
        Tag newTag = new Tag();
        newTag.setName(tag.getName());
        entityManager.persist(newTag);
        return newTag;
    }

    @Override
    @Transactional
    public Tag delete(Long deleteId) {
        Tag tag = entityManager.find(Tag.class, deleteId);
        entityManager.remove(tag);
        return tag;
    }

    @Override
    public List<Tag> findByNativeSpecification(NativeSpecification specification) {
        String query = specification.getNativeQuery();
        return entityManager.createNativeQuery(query, Tag.class).getResultList();
    }

    @Override
    public Long countTags() {
        return (Long) entityManager.createQuery("select count(id) from Tag").getSingleResult();
    }
}