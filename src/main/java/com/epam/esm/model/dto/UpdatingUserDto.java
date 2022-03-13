package com.epam.esm.model.dto;

import com.epam.esm.model.entity.User;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UpdatingUserDto {

    String email;

    String password;

    String firstName;

    String lastName;

}
