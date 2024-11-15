package org.example.projectfortest.service;

import org.example.projectfortest.dtos.UserDeleteDto;
import org.example.projectfortest.dtos.UserDto;
import org.example.projectfortest.dtos.UserRegisterDto;

import java.util.List;

public interface UserService {
    UserDto save(UserRegisterDto user);
    UserDto findById(Long id);
    UserDto findByLogin(String login);
    void delete(UserDeleteDto user);
    UserDto update(UserRegisterDto user);
    List<UserDto> findAll();

}
