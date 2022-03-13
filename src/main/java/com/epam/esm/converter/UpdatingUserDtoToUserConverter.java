package com.epam.esm.converter;

import com.epam.esm.model.dto.UpdatingUserDto;
import com.epam.esm.model.entity.User;
import org.springframework.core.convert.converter.Converter;

public class UpdatingUserDtoToUserConverter implements Converter<UpdatingUserDto, User> {

    @Override
    public User convert(UpdatingUserDto source) {
        User user = new User();
        user.setEmail(source.getEmail());
        user.setPassword(source.getPassword());
        user.setFirstName(source.getFirstName());
        user.setLastName(source.getLastName());
        return user;
    }
}
