package com.epam.esm.controller;

import com.epam.esm.controller.hateoas.api.UserControllerHateoasLinkBuilder;
import com.epam.esm.model.dto.*;
import com.epam.esm.service.api.OrderService;
import com.epam.esm.service.api.TagService;
import com.epam.esm.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final OrderService orderService;
    private final TagService tagService;
    private final UserControllerHateoasLinkBuilder linkBuilder;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<UserDto> read(@RequestParam(name = "page", required = false, defaultValue = "0")
                                         @Min(value = 0, message = "Page size should be greater than or equal to zero")
                                                 Long pageNum,
                                         @RequestParam(name = "pageSize", required = false, defaultValue = "50")
                                         @Min(value = 1, message = "Page size should be represented as positive number")
                                         @Max(value = 100, message = "Page size should not be greater than 100")
                                                 Long pageSize) {
        Long usersCount = userService.countUsers();
        List<UserDto> userDtos = userService.findAll(pageNum, pageSize);
        return linkBuilder.buildReadAllLinks(userDtos, usersCount, pageNum, pageSize);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto read(@PathVariable Long id) {
        UserDto dto = userService.findById(id);
        return linkBuilder.buildReadSingleLinks(dto);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto update(@PathVariable Long id, @RequestBody UpdatingUserDto user) {
        UserDto dto = userService.update(id, user);
        return linkBuilder.buildUpdateLinks(dto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto delete(@PathVariable Long id) {
        UserDto dto = userService.delete(id);
        return linkBuilder.buildDeleteLinks(dto);
    }

    @GetMapping(value = "/{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<OrderDto> readOrders(@PathVariable Long id) {
        List<OrderDto> orderDtos = orderService.findByUserId(id);
        return linkBuilder.buildReadAllOrdersLinks(orderDtos, id);
    }

    @GetMapping(value = "{userId}/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto readOrder(@PathVariable Long userId, @PathVariable Long orderId) {
        OrderDto orderDto = orderService.findById(orderId);
        return linkBuilder.buildReadSingleOrderLinks(orderDto);
    }

    @PostMapping(value = "/{id}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@PathVariable Long id, @RequestBody List<Long> certificatesIds) {
        OrderDto dto = orderService.create(id, certificatesIds);
        return linkBuilder.buildCreateOrderLinks(dto, certificatesIds);
    }

    @GetMapping(value = "/{userId}/popular_tags")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<TagDto> getPopularUserTags(@PathVariable Long userId) {
        List<TagDto> tagDtos = tagService.findMostUsedUserTag(userId);
        return linkBuilder.buildGetPopularUserTagsLinks(userId, tagDtos);
    }

}
