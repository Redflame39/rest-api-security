package com.epam.esm.repository.impl;

import com.epam.esm.model.entity.User;
import com.epam.esm.repository.api.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository<Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll(Integer pageNum, Integer pageSize) {
        TypedQuery<User> query = entityManager.createQuery("from User", User.class);
        query.setFirstResult((pageNum - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        Predicate usernamePredicate = criteriaBuilder.equal(root.get("email"), username);
        criteriaQuery.select(root).where(usernamePredicate);
        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        try {
            return Optional.of(typedQuery.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public User create(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    @Transactional
    public User update(Long updateId, User dto) {
        User user = entityManager.find(User.class, updateId);
        entityManager.detach(user);
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        entityManager.merge(user);
        return user;
    }

    @Override
    @Transactional
    public User delete(Long deleteId) {
        User user = entityManager.find(User.class, deleteId);
        entityManager.remove(user);
        return user;
    }

    @Override
    public Long countUsers() {
        return (Long) entityManager.createQuery("select count(id) from User").getSingleResult();
    }
}
