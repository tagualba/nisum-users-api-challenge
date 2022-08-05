package com.nisum.users.services;

import com.nisum.users.exceptions.ValidationException;
import com.nisum.users.model.domain.User;
import com.nisum.users.model.dtos.UserRequestDto;
import com.nisum.users.model.dtos.UserResponseDto;
import com.nisum.users.repositories.UserRepository;
import com.nisum.users.statics.ErrorCode;
import com.nisum.users.translators.UserTranslator;
import com.nisum.users.util.EncryptUtil;
import com.nisum.users.util.JwtUtil;
import com.nisum.users.validators.UserValidator;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserValidator userValidator;

    private UserTranslator userTranslator;

    private UserRepository userRepository;

    private JwtUtil jwtUtil;

    public UserService(UserValidator userValidator, UserRepository userRepository, JwtUtil jwtUtil, UserTranslator userTranslator){
        this.userValidator = userValidator;
        this.userTranslator = userTranslator;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }
    public UserResponseDto createUser(UserRequestDto request) throws ValidationException {
        System.out.println(String.format("Event: %s, Request: %s","UserService.createUser", request.toString()));

        userValidator.validateCreateUserRequest(request);

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw  new ValidationException(ErrorCode.REPEAT_EMAIL, "El correo ya registrado.");
        }

        String hashPass = EncryptUtil.createHash(request.getEmail(), request.getPassword());
        String tokenJwt = jwtUtil.createApiToken(request.getEmail());

        User user = userTranslator.createUserToDomain(request, hashPass, LocalDateTime.now(), tokenJwt);

        return userTranslator.toResponse(userRepository.save(user));
    }

    public UserResponseDto login(UserRequestDto request) throws ValidationException {
        System.out.println(String.format("Event: %s, Request: %s","UserService.login", request.toString()));

        userValidator.validateLoginRequest(request);

        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if(!user.isPresent() || !EncryptUtil.checkHash(request.getEmail(), request.getPassword(), user.get().getHashPassword())){
            throw  new ValidationException(ErrorCode.COMBINATION_FAIL, "Combinación erronea.");
        }

        if(jwtUtil.validate(user.get().getLastTokenApi())){
            return userTranslator.toResponse(user.get());
        }

        String tokenJwt = jwtUtil.createApiToken(request.getEmail());

        User userUpdate = User.newBuilder(user.get()).withLastTokenApi(tokenJwt).build();

        return userTranslator.toResponse(userRepository.save(userUpdate));
    }

    public UserResponseDto getUserByToken(String token) throws ValidationException {
        System.out.println(String.format("Event: %s","UserService.getUserByToken"));

        String email = jwtUtil.getEmailUserByToken(token);
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            return userTranslator.toResponse(user.get());
        }

        throw new ValidationException(ErrorCode.INVALID_TOKEN, "Token invalido");
    }

    public UserResponseDto getUserByLastToken(String email) throws ValidationException {
        System.out.println(String.format("Event: %s, Email: %s","UserService.getUserByLastToken", email));
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent() && jwtUtil.validate(user.get().getLastTokenApi())){
           return userTranslator.toResponse(user.get());
        }
        throw new ValidationException(ErrorCode.EXPIRED_TOKEN, "Token expirado.");
    }
}
