package com.epam.esm.service.api;

import com.epam.esm.model.dto.OrderDto;

import java.util.List;

/**
 * The interface Order service describes create and read operations for {@code Order entity}.
 */
public interface OrderService {

    /**
     * Find all orders of the user with specified id.
     *
     * @param userId the id of user
     * @return the list of found orders
     */
    List<OrderDto> findByUserId(Long userId);

    /**
     * Find order by its id.
     *
     * @param id the id of order
     * @return the order dto of found order
     */
    OrderDto findById(Long id);

    /**
     * Create order containing specified gift certificates.
     *
     * @param userId          the user id
     * @param certificatesIds the list of certificates ids which should be in the order
     * @return the order dto of created order
     */
    OrderDto create(Long userId, List<Long> certificatesIds);
}
