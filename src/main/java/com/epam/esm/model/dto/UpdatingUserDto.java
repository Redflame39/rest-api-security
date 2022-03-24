package com.epam.esm.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UpdatingUserDto {

    String email;

    String password;

}
