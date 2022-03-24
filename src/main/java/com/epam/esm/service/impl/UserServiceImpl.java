package com.epam.esm.service.impl;

import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.model.dto.AuthenticatingDto;
import com.epam.esm.model.dto.UpdatingUserDto;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.entity.Role;
import com.epam.esm.model.entity.User;
import com.epam.esm.repository.api.UserRepository;
import com.epam.esm.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final ConversionService conversionService;
    private final UserRepository<Long> userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> findAll(Integer pageNum, Integer pageSize) {
        List<User> users = userRepository.findAll(pageNum, pageSize);
        return UserDto.toUserDtoList(users);
    }

    @Override
    public UserDto findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        User item = user.orElseThrow(() -> new EntityNotFoundException("Cannot find user with id " + id));
        return conversionService.convert(item, UserDto.class);
    }

    @Override
    public UserDto create(AuthenticatingDto userDto) {
        User user = conversionService.convert(userDto, User.class);
        Role role = new Role();
        role.setName("ROLE_USER");
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User created = userRepository.create(user);
        return conversionService.convert(created, UserDto.class);
    }

    @Override
    public UserDto update(Long updateId, UpdatingUserDto userDto) {
        User user = conversionService.convert(userDto, User.class);
        User updated = userRepository.update(updateId, user);
        return conversionService.convert(updated, UserDto.class);
    }

    @Override
    public UserDto delete(Long deleteId) {
        User deleted = userRepository.delete(deleteId);
        return conversionService.convert(deleted, UserDto.class);
    }

    @Override
    public Long countUsers() {
        return userRepository.countUsers();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new UsernameNotFoundException("Requested user not found, username: " + username));
    }

}
