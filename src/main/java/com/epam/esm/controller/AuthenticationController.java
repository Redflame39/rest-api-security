package com.epam.esm.controller;

import com.epam.esm.model.dto.AuthenticatingDto;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.security.api.AuthenticationService;
import com.epam.esm.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestBody AuthenticatingDto dto) {
        return authenticationService.authenticate(dto);
    }

    @PostMapping(value = "/signin")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto signIn(@RequestBody AuthenticatingDto dto) {
        return userService.create(dto);
    }
}
