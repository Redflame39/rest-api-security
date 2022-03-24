package com.epam.esm.controller;

import com.epam.esm.model.dto.*;
import com.epam.esm.service.api.OrderService;
import com.epam.esm.service.api.TagService;
import com.epam.esm.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final OrderService orderService;
    private final TagService tagService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<UserDto> read(@RequestParam(name = "page", required = false, defaultValue = "1")
                                         @Min(value = 1, message = "Page should be represented as positive number")
                                                 Integer pageNum,
                                         @RequestParam(name = "pageSize", required = false, defaultValue = "50")
                                         @Min(value = 1, message = "Page size should be represented as positive number")
                                         @Max(value = 100, message = "Page size should not be greater than 100")
                                                 Integer pageSize) {
        List<UserDto> userDtos = userService.findAll(pageNum, pageSize);
        Long usersCount = userService.countUsers();
        for (UserDto dto : userDtos) {
            Link certificateLink = linkTo(methodOn(CertificateController.class)
                    .read(dto.getId()))
                    .withSelfRel();
            dto.add(certificateLink);
        }
        List<Link> collectionLinks = new ArrayList<>();
        if (pageNum != 1) {
            Link prevPageLink = linkTo(methodOn(UserController.class)
                    .read(pageNum - 1, pageSize))
                    .withRel("Previous page");
            collectionLinks.add(prevPageLink);
        }
        Link currentPageLink = linkTo(methodOn(UserController.class)
                .read(pageNum, pageSize))
                .withRel("Current page");
        boolean isLastPage = (long) pageNum * pageSize >= usersCount;
        if (!isLastPage) {
            Link nextPageLink = linkTo(methodOn(UserController.class)
                    .read(pageNum + 1, pageSize))
                    .withRel("Next page");
            collectionLinks.add(nextPageLink);
        }
        collectionLinks.add(currentPageLink);
        return CollectionModel.of(userDtos, collectionLinks);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto read(@PathVariable Long id) {
        UserDto dto = userService.findById(id);
        Link self = linkTo(methodOn(UserController.class)
                .read(id))
                .withSelfRel();
        dto.add(self);
        return dto;
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto update(@PathVariable Long id, @RequestBody UpdatingUserDto user) {
        UserDto dto = userService.update(id, user);
        Link self = linkTo(methodOn(UserController.class)
                .update(id, user))
                .withSelfRel();
        Link toUpdatedLink = linkTo(methodOn(UserController.class)
                .read(dto.getId()))
                .withRel("Read updated");
        dto.add(self, toUpdatedLink);
        return dto;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto delete(@PathVariable Long id) {
        UserDto dto = userService.delete(id);
        Link self = linkTo(methodOn(UserController.class)
                .delete(id))
                .withSelfRel();
        dto.add(self);
        return dto;
    }

    @GetMapping(value = "/{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<OrderDto> readOrders(@PathVariable Long id) {
        List<OrderDto> orderDtos = orderService.findByUserId(id);
        for (OrderDto orderDto : orderDtos) {
            Link orderSelf = linkTo(methodOn(UserController.class)
                    .readOrder(id, orderDto.getId()))
                    .withSelfRel();
            orderDto.add(orderSelf);
            for (CertificateDto certificateDto : orderDto.getCertificates()) {
                Link certificateSelf = linkTo(methodOn(CertificateController.class)
                        .read(certificateDto.getId()))
                        .withSelfRel();
                certificateDto.add(certificateSelf);
            }
        }
        Link self = linkTo(methodOn(UserController.class)
                .readOrders(id))
                .withSelfRel();
        return CollectionModel.of(orderDtos, self);
    }

    @GetMapping(value = "{userId}/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto readOrder(@PathVariable Long userId, @PathVariable Long orderId) {
        OrderDto orderDto = orderService.findById(orderId);
        Link self = linkTo(methodOn(UserController.class)
                .readOrder(userId, orderId))
                .withSelfRel();
        orderDto.add(self);
        return orderDto;
    }

    @PostMapping(value = "/{id}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@PathVariable Long id, @RequestBody List<Long> certificatesIds) {
        OrderDto dto = orderService.create(id, certificatesIds);
        Link self = linkTo(methodOn(UserController.class)
                .createOrder(id, certificatesIds))
                .withSelfRel();
        Link toUserOrders = linkTo(methodOn(UserController.class)
                .readOrders(id))
                .withRel("Read all user orders");
        dto.add(self, toUserOrders);
        return dto;
    }

    @GetMapping(value = "/{userId}/popular_tags")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<TagDto> getPopularUserTags(@PathVariable Long userId) {
        List<TagDto> tagDtos = tagService.findMostUsedUserTag(userId);
        for (TagDto dto : tagDtos) {
            Link self = linkTo(methodOn(TagController.class)
                    .read(dto.getId()))
                    .withSelfRel();
            dto.add(self);
        }
        Link self = linkTo(methodOn(UserController.class)
                .getPopularUserTags(userId))
                .withSelfRel();
        return CollectionModel.of(tagDtos, self);
    }

}
