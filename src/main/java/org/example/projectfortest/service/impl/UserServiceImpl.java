package org.example.projectfortest.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.projectfortest.entity.UserEntity;
import org.example.projectfortest.dtos.UserDeleteDto;
import org.example.projectfortest.dtos.UserDto;
import org.example.projectfortest.dtos.UserRegisterDto;
import org.example.projectfortest.mappers.UserEntityToUserDto;
import org.example.projectfortest.repo.UserRepository;
import org.example.projectfortest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto save(UserRegisterDto user) {
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("UserRegisterDto is null");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("UserRegisterDto name is null or empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("UserRegisterDto password is null or empty");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new IllegalArgumentException("UserRegisterDto login is null or empty");
        }
        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new IllegalArgumentException("UserRegisterDto login already exists");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(user.getLogin());
        userEntity.setName(user.getName());
        userEntity.setPassword(user.getPassword());
        userRepository.save(userEntity);
        log.info("User {} saved with ID:{}", userEntity.getLogin(), userEntity.getId());
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setLogin(user.getLogin());
        userDto.setName(user.getName());
        return userDto;
    }

    @Override
    public UserDto findById(Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("UserRegisterDto id is null");
        }
        if (id <= 0){
            throw new IllegalArgumentException("UserRegisterDto id must be greater than 0");
        }
        UserEntity userEntity = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setLogin(userEntity.getLogin());
        userDto.setName(userEntity.getName());
        return userDto;
    }

    @Override
    public UserDto findByLogin(String login) {
        if (Objects.isNull(login) || login.isEmpty()) {
            throw new IllegalArgumentException("UserRegisterDto login is null or empty");
        }
        UserEntity userEntity = userRepository.findByLogin(login).orElseThrow(EntityNotFoundException::new);
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setLogin(userEntity.getLogin());
        userDto.setName(userEntity.getName());
        return userDto;
    }

    @Override
    public void delete(UserDeleteDto user) {
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("UserRegisterDto is null");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new IllegalArgumentException("UserRegisterDto login is null or empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("UserRegisterDto password is null or empty");
        }
        Optional<UserEntity> userOptional = userRepository.findByLogin(user.getLogin());
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("UserRegisterDto login does not exist");
        }
        if (userOptional.get().getLogin().equals(user.getLogin()) && userOptional.get().getPassword().equals(user.getPassword())) {
            userRepository.delete(userOptional.get());
            log.info("User {} deleted with ID:{}", user.getLogin(), userOptional.get().getId());
            return;
        }
        throw new IllegalArgumentException("UserRegisterDto login does not exist");
    }

    @Override
    public UserDto update(UserRegisterDto user) {
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("UserRegisterDto is null");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new IllegalArgumentException("UserRegisterDto login is null or empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("UserRegisterDto password is null or empty");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new IllegalArgumentException("UserRegisterDto login is null or empty");
        }
        Optional<UserEntity> userOptional = userRepository.findByLogin(user.getLogin());
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("UserRegisterDto login does not exist");
        }
        if (userOptional.get().getLogin().equals(user.getLogin()) && userOptional.get().getPassword().equals(user.getPassword())) {
            userRepository.delete(userOptional.get());
            log.info("User {} updated with ID:{}", user.getLogin(), userOptional.get().getId());
            UserDto userDto = new UserDto();
            userDto.setId(userOptional.get().getId());
            userDto.setName(userOptional.get().getName());
            userDto.setLogin(userOptional.get().getLogin());
            return userDto;
        }
        throw new IllegalArgumentException("UserRegisterDto login does not exist");
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(u -> {
            UserEntityToUserDto userDto = new UserEntityToUserDto();
            return userDto.toUserDto(u);
        }).collect(Collectors.toList());
    }
}
