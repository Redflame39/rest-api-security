package com.epam.esm.service.impl;

import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.model.dto.AuthenticatingDto;
import com.epam.esm.model.dto.UpdatingUserDto;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.entity.Role;
import com.epam.esm.model.entity.User;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService, UserDetailsService {

    private final ConversionService conversionService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> findAll(Long pageNum, Long pageSize) {
        PageRequest pageRequest;
        Page<User> users = userRepository.findAll(PageRequest.of(pageNum.intValue(), pageSize.intValue()));
        return UserDto.toUserDtoList(users.toList());
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
        User created = userRepository.save(user);
        return conversionService.convert(created, UserDto.class);
    }

    @Override
    public UserDto update(Long updateId, UpdatingUserDto userDto) {
        User user = conversionService.convert(userDto, User.class);
        user.setId(updateId);
        User updated = userRepository.save(user);
        return conversionService.convert(updated, UserDto.class);
    }

    @Override
    public UserDto delete(Long deleteId) {
        Optional<User> toDelete = userRepository.findById(deleteId);
        if (!toDelete.isPresent()) {
            throw new EntityNotFoundException("Failed to find user with id " + deleteId);
        }
        User entity = toDelete.get();
        userRepository.delete(entity);
        return conversionService.convert(entity, UserDto.class);
    }

    @Override
    public Long countUsers() {
        return userRepository.count();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        return user.orElseThrow(() -> new UsernameNotFoundException("Requested user not found, username: " + username));
    }

}
