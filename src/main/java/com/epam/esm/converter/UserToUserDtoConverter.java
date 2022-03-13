package com.epam.esm.converter;

import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.entity.User;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserToUserDtoConverter implements Converter<User, UserDto> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSS");

    @Override
    public UserDto convert(User source) {
        UserDto userDto = new UserDto();
        userDto.setId(source.getId());
        userDto.setEmail(source.getEmail());
        userDto.setPassword(source.getPassword());
        userDto.setFirstName(source.getFirstName());
        userDto.setLastName(source.getLastName());

        LocalDateTime createLocalDateTime = source.getCreateDate().toLocalDateTime();
        LocalDateTime updateLocalDateTime = source.getLastUpdateDate().toLocalDateTime();
        String createDate = createLocalDateTime.format(FORMATTER);
        String updateDate = updateLocalDateTime.format(FORMATTER);
        userDto.setCreateDate(createDate);
        userDto.setLastUpdateDate(updateDate);
        return userDto;
    }
}
