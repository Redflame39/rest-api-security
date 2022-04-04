package com.epam.esm.converter;

import com.epam.esm.model.dto.AuthenticatingDto;
import com.epam.esm.model.entity.User;
import org.springframework.core.convert.converter.Converter;

public class AuthenticatingUserDtoToUserConverter implements Converter<AuthenticatingDto, User> {

    @Override
    public User convert(AuthenticatingDto source) {
        User user = new User();
        user.setEmail(source.getLogin());
        user.setPassword(source.getPassword());
        return user;
    }

}
