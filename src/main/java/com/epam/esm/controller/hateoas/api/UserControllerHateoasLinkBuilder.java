package com.epam.esm.controller.hateoas.api;

import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.dto.UserDto;
import org.springframework.hateoas.CollectionModel;

import java.util.Collection;
import java.util.List;

public interface UserControllerHateoasLinkBuilder {

    CollectionModel<UserDto> buildReadAllLinks(Collection<UserDto> userDtos, Long usersCount,
                                               Long pageNum, Long pageSize);

    UserDto buildReadSingleLinks(UserDto userDto);

    UserDto buildUpdateLinks(UserDto userDto);

    UserDto buildDeleteLinks(UserDto userDto);

    CollectionModel<OrderDto> buildReadAllOrdersLinks(Collection<OrderDto> orderDtos, Long userId);

    OrderDto buildReadSingleOrderLinks(OrderDto orderDto);

    OrderDto buildCreateOrderLinks(OrderDto orderDto, List<Long> certificatesIds);

    CollectionModel<TagDto> buildGetPopularUserTagsLinks(Long userId, Collection<TagDto> tagDtos);

}
