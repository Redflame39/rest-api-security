package com.epam.esm.model.dto;

import com.epam.esm.converter.UserToUserDtoConverter;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class UserDto extends RepresentationModel<UserDto> {

    Long id;

    String email;

    String createDate;

    String lastUpdateDate;

    List<OrderDto> orders;

    public static List<UserDto> toUserDtoList(Collection<User> users) {
        UserToUserDtoConverter converter = new UserToUserDtoConverter();
        return users.stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

}
