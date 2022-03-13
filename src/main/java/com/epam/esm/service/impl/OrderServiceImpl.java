package com.epam.esm.service.impl;

import com.epam.esm.exception.EntityNotCreatedException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.repository.api.CertificateRepository;
import com.epam.esm.repository.api.OrderRepository;
import com.epam.esm.repository.api.UserRepository;
import com.epam.esm.service.api.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService {

    private final OrderRepository<Long> orderRepository;
    private final UserRepository<Long> userRepository;
    private final CertificateRepository<Long> certificateRepository;
    private final ConversionService conversionService;

    @Override
    public List<OrderDto> findByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        User item = user.orElseThrow(() -> new EntityNotFoundException("Cannot find user with id " + userId));
        List<Order> userOrders = new ArrayList<>(item.getOrders());
        return OrderDto.toOrderDtoList(userOrders);
    }

    @Override
    public OrderDto findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        Order item = order.orElseThrow(() -> new EntityNotFoundException("Failed to find order with id " + id));
        return conversionService.convert(item, OrderDto.class);
    }

    @Override
    @Transactional
    public OrderDto create(Long userId, List<Long> certificatesIds) {
        if (certificatesIds.isEmpty()) {
            throw new EntityNotCreatedException("Cannot create order without certificates.");
        }
        Order order = new Order();
        Optional<User> user = userRepository.findById(userId);
        User userItem = user.orElseThrow(
                () -> new EntityNotFoundException("Cannot find user to create order, user id: " + userId));
        order.setUser(userItem);
        Set<Certificate> orderedCertificates = loadCertificates(certificatesIds);
        order.setCertificates(orderedCertificates);
        Double price = calculatePrice(orderedCertificates);
        order.setPrice(price);
        Order newOrder = orderRepository.create(order);
        return conversionService.convert(newOrder, OrderDto.class);
    }

    private Set<Certificate> loadCertificates(List<Long> certificatesIds) {
        Set<Certificate> orderedCertificates = new HashSet<>();
        for (Long certificateId : certificatesIds) {
            Optional<Certificate> certificate = certificateRepository.findById(certificateId);
            Certificate certificateItem = certificate.orElseThrow(
                    () -> new EntityNotFoundException("Cannot find ordered certificate, id: " + certificateId));
            orderedCertificates.add(certificateItem);
        }
        return orderedCertificates;
    }

    private Double calculatePrice(Collection<Certificate> certificates) {
        return certificates.stream()
                .mapToDouble(Certificate::getPrice)
                .sum();
    }
}
