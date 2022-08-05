package com.nisum.users.controllers;

import com.nisum.users.exceptions.ValidationException;
import com.nisum.users.model.dtos.UserRequestDto;
import com.nisum.users.model.dtos.UserResponseDto;
import com.nisum.users.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/nisum/users")
@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto request)
        throws ValidationException {
        return userService.createUser(request);
    }

    @PostMapping(value ="/login")
    public UserResponseDto login(@RequestBody UserRequestDto request) throws ValidationException {
        return userService.login(request);
    }

    @GetMapping(value ="/check-token")
    public UserResponseDto getUserByToken(@RequestParam String token) throws ValidationException {
        return userService.getUserByToken(token);
    }

    @GetMapping(value = "/check-last-token")
    public UserResponseDto getUserByLastToken(@RequestParam String email) throws ValidationException {
        return userService.getUserByLastToken(email);
    }
}
