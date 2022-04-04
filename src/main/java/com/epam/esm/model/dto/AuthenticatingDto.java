package com.epam.esm.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AuthenticatingDto {

    String login;

    String password;

}
