package com.epam.esm.service.api;

import com.epam.esm.model.dto.UpdatingUserDto;
import com.epam.esm.model.dto.UserDto;

import java.util.List;

/**
 * The interface User service describes CRUD operations for {@code User} entity.
 */
public interface UserService {

    /**
     * Find all users. Supports pagination
     *
     * @param pageNum  the number of page
     * @param pageSize the size of page
     * @return list of users found
     */
    List<UserDto> findAll(Integer pageNum, Integer pageSize);

    /**
     * Find by user by id.
     *
     * @param id the user id
     * @return the {@code UserDto} of user found
     */
    UserDto findById(Long id);

    /**
     * Create user with given in {@code UpdatingUserDto} attributes.
     *
     * @param userDto the user dto with creating attributes
     * @return the created user dto
     */
    UserDto create(UpdatingUserDto userDto);

    /**
     * Update user by id with given in {@code UpdatingUserDto} attributes.
     *
     * @param updateId the update id
     * @param userDto  the user dto
     * @return the updated user dto
     */
    UserDto update(Long updateId, UpdatingUserDto userDto);

    /**
     * Delete user by id.
     *
     * @param deleteId the delete id
     * @return the deleted user dto
     */
    UserDto delete(Long deleteId);

    /**
     * Count users in database.
     *
     * @return users count
     */
    Long countUsers();

}
