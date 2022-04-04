package com.epam.esm.controller.hateoas.impl;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.controller.hateoas.api.UserControllerHateoasLinkBuilder;
import com.epam.esm.model.dto.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserControllerHateoasLinkBuilderImpl implements UserControllerHateoasLinkBuilder {

    @Override
    public CollectionModel<UserDto> buildReadAllLinks(Collection<UserDto> userDtos,
                                                      Long usersCount, Long pageNum, Long pageSize) {
        for (UserDto dto : userDtos) {
            Link certificateLink = linkTo(methodOn(CertificateController.class)
                    .read(dto.getId()))
                    .withSelfRel();
            dto.add(certificateLink);
        }
        List<Link> collectionLinks = new ArrayList<>();
        if (pageNum != 0) {
            Link prevPageLink = linkTo(methodOn(UserController.class)
                    .read(pageNum - 1, pageSize))
                    .withRel("Previous page");
            collectionLinks.add(prevPageLink);
        }
        Link currentPageLink = linkTo(methodOn(UserController.class)
                .read(pageNum, pageSize))
                .withRel("Current page");
        boolean isLastPage = pageNum * pageSize >= usersCount;
        if (!isLastPage) {
            Link nextPageLink = linkTo(methodOn(UserController.class)
                    .read(pageNum + 1, pageSize))
                    .withRel("Next page");
            collectionLinks.add(nextPageLink);
        }
        collectionLinks.add(currentPageLink);
        return CollectionModel.of(userDtos, collectionLinks);
    }

    @Override
    public UserDto buildReadSingleLinks(UserDto userDto) {
        Link self = linkTo(methodOn(UserController.class)
                .read(userDto.getId()))
                .withSelfRel();
        userDto.add(self);
        return userDto;
    }

    @Override
    public UserDto buildUpdateLinks(UserDto userDto) {
        UpdatingUserDto placeholderUserDto = new UpdatingUserDto();
        Link self = linkTo(methodOn(UserController.class)
                .update(userDto.getId(), placeholderUserDto))
                .withSelfRel();
        Link toUpdatedLink = linkTo(methodOn(UserController.class)
                .read(userDto.getId()))
                .withRel("Read updated");
        userDto.add(self, toUpdatedLink);
        return userDto;
    }

    @Override
    public UserDto buildDeleteLinks(UserDto userDto) {
        Link self = linkTo(methodOn(UserController.class)
                .delete(userDto.getId()))
                .withSelfRel();
        userDto.add(self);
        return userDto;
    }

    @Override
    public CollectionModel<OrderDto> buildReadAllOrdersLinks(Collection<OrderDto> orderDtos, Long userId) {
        for (OrderDto orderDto : orderDtos) {
            Link orderSelf = linkTo(methodOn(UserController.class)
                    .readOrder(orderDto.getId(), orderDto.getId()))
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
                .readOrders(userId))
                .withSelfRel();
        return CollectionModel.of(orderDtos, self);
    }

    @Override
    public OrderDto buildReadSingleOrderLinks(OrderDto orderDto) {
        Link self = linkTo(methodOn(UserController.class)
                .readOrder(orderDto.getUserId(), orderDto.getId()))
                .withSelfRel();
        orderDto.add(self);
        return orderDto;
    }

    @Override
    public OrderDto buildCreateOrderLinks(OrderDto orderDto, List<Long> certificatesIds) {
        Link self = linkTo(methodOn(UserController.class)
                .createOrder(orderDto.getId(), certificatesIds))
                .withSelfRel();
        Link toUserOrders = linkTo(methodOn(UserController.class)
                .readOrders(orderDto.getId()))
                .withRel("Read all user orders");
        orderDto.add(self, toUserOrders);
        return orderDto;
    }

    @Override
    public CollectionModel<TagDto> buildGetPopularUserTagsLinks(Long userId, Collection<TagDto> tagDtos) {
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
