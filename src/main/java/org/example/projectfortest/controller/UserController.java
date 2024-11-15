package org.example.projectfortest.controller;

import org.example.projectfortest.dtos.UserDeleteDto;
import org.example.projectfortest.dtos.UserDto;
import org.example.projectfortest.dtos.UserRegisterDto;
import org.example.projectfortest.service.UserService;
import org.example.projectfortest.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/get-all")
    public List<UserDto> findAll(){
        return userService.findAll();
    }

    @PostMapping(value = "/create")
    public UserDto save(@RequestBody UserRegisterDto userRegisterDto) {
        return userService.save(userRegisterDto);
    }

    @GetMapping(value = "/get-by/{id}")
    public UserDto findById(@PathVariable Long id){
        return userService.findById(id);
    }

    @GetMapping(value = "/get-by/{login}")
    public UserDto findByLogin(@PathVariable String login){
        return userService.findByLogin(login);
    }

    @PutMapping(value = "/update")
    public UserDto update(@RequestBody UserRegisterDto userRegisterDto){
        return userService.update(userRegisterDto);
    }

    @DeleteMapping(value = "delete-by/dto")
    public void delete(@RequestBody UserDeleteDto userDeleteDto){
        userService.delete(userDeleteDto);
    }
}
