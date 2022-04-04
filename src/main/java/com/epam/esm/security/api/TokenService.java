package com.epam.esm.security.api;

public interface TokenService {

    String generateToken(String username, String password);

    boolean validateToken(String token);

    String getLoginFromToken(String token);
}
