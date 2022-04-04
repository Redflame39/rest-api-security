package com.epam.esm.security.api;

import com.epam.esm.model.dto.AuthenticatingDto;

public interface AuthenticationService {

    String authenticate(AuthenticatingDto dto);

}
