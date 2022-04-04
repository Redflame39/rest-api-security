package com.epam.esm.security.impl;

import com.epam.esm.model.dto.AuthenticatingDto;
import com.epam.esm.security.api.AuthenticationService;
import com.epam.esm.security.api.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Override
    public String authenticate(AuthenticatingDto dto) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return tokenService.generateToken(userDetails.getUsername(), userDetails.getPassword());
    }

}
