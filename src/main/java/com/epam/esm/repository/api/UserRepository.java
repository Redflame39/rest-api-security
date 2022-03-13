package com.epam.esm.repository.api;

import com.epam.esm.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * The interface User repository.
 *
 * @param <K> the type parameter
 */
public interface UserRepository<K> {

    /**
     * Find all list.
     *
     * @param pageNum  the page num
     * @param pageSize the page size
     * @return the list
     */
    List<User> findAll(Integer pageNum, Integer pageSize);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<User> findById(K id);

    /**
     * Create user.
     *
     * @param user the user
     * @return the user
     */
    User create(User user);

    /**
     * Update user.
     *
     * @param updateId the update id
     * @param dto      the dto
     * @return the user
     */
    User update(K updateId, User dto);

    /**
     * Delete user.
     *
     * @param deleteId the delete id
     * @return the user
     */
    User delete(K deleteId);

    /**
     * Count users in database.
     *
     * @return users count
     */
    Long countUsers();

}
