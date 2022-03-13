package com.epam.esm.model.dto;

import com.epam.esm.converter.UserToUserDtoConverter;
import com.epam.esm.model.entity.User;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class UserDto extends RepresentationModel<UserDto> {

    Long id;

    String email;

    String password;

    String firstName;

    String lastName;

    String createDate;

    String lastUpdateDate;

    public static List<UserDto> toUserDtoList(List<User> users) {
        UserToUserDtoConverter converter = new UserToUserDtoConverter();
        return users.stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

}
