package com.nisum.users.unit.services;

import com.nisum.users.exceptions.ValidationException;
import com.nisum.users.model.domain.Phone;
import com.nisum.users.model.domain.User;
import com.nisum.users.model.dtos.PhoneDto;
import com.nisum.users.model.dtos.UserRequestDto;
import com.nisum.users.model.dtos.UserResponseDto;
import com.nisum.users.repositories.UserRepository;
import com.nisum.users.services.UserService;
import com.nisum.users.statics.ErrorCode;
import com.nisum.users.statics.UserStatus;
import com.nisum.users.translators.UserTranslator;
import com.nisum.users.util.EncryptUtil;
import com.nisum.users.util.JwtUtil;
import com.nisum.users.validators.UserValidator;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserRequestDto userRequestDto;

    private UserResponseDto userResponseDto;

    private UserService userService;

    @Mock
    private UserValidator userValidator;

    @Mock
    private UserRepository userRepository;

    private UserTranslator userTranslator;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    public void init() {
        userService = new UserService(userValidator, userRepository, jwtUtil, new UserTranslator());
    }

    @Test
    public void createUserOk() throws ValidationException {
        userRequestDto = UserRequestDto.newBuilder()
                                       .withName("name_test")
                                       .withEmail("email@test.com")
                                       .withPassword("Passss.44819")
                                       .withPhones(
                                           Lists.newArrayList(
                                               PhoneDto.newBuilder()
                                                       .withNumber("123456")
                                                       .withCityCode("11")
                                                       .withCountryCode("54")
                                                       .build()
                                                             ))
                                       .build();
        LocalDateTime creationDate = LocalDateTime.now();
        UUID id = UUID.randomUUID();

        doNothing().when(userValidator).validateCreateUserRequest(any());

        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(jwtUtil.createApiToken(any())).thenReturn("token_api_jwt_test");
        when(userRepository.save(any())).thenReturn(User.newBuilder()
                                                        .withId(id)
                                                        .withName("name_test")
                                                        .withEmail("email@test.com")
                                                        .withCreationDate(creationDate)
                                                        .withLastUpdateDate(creationDate)
                                                        .withLastLoginDate(creationDate)
                                                        .withStatus(UserStatus.ENABLED)
                                                        .withLastTokenApi("last_token_test")
                                                        .withPhones(Lists.newArrayList(
                                                            Phone.newBuilder()
                                                                 .withNumber("123456")
                                                                 .withCityCode("11")
                                                                 .withCountryCode("54")
                                                                 .build()
                                                                                      ))
                                                        .build());

        userResponseDto = userService.createUser(userRequestDto);

        assertEquals(userResponseDto.getId(), id.toString());
        assertEquals(userResponseDto.getCreated(), creationDate.toString());
        assertEquals(userResponseDto.getLastLogin(), creationDate.toString());
        assertEquals(userResponseDto.getIsActive(), UserStatus.ENABLED.name());
        assertEquals(userResponseDto.getToken(), "last_token_test");
    }

    @Test
    public void createUserInvalidDataFail() throws ValidationException {
        userRequestDto = UserRequestDto.newBuilder()
                                       .withEmail("email@test.com")
                                       .withPassword("Passss.44819")
                                       .withPhones(
                                           Lists.newArrayList(
                                               PhoneDto.newBuilder()
                                                       .withNumber("123456")
                                                       .withCityCode("11")
                                                       .withCountryCode("54")
                                                       .build()
                                                             ))
                                       .build();
        LocalDateTime creationDate = LocalDateTime.now();
        UUID id = UUID.randomUUID();

        doThrow(new ValidationException(ErrorCode.INVALID_DATA)).when(userValidator).validateCreateUserRequest(any());

        assertThrows(ValidationException.class, () -> userService.createUser(userRequestDto));
    }

    @Test
    public void createUserRepeatMailFail() throws ValidationException {
        userRequestDto = UserRequestDto.newBuilder()
                                       .withEmail("email@test.com")
                                       .withPassword("Passss.44819")
                                       .withPhones(
                                           Lists.newArrayList(
                                               PhoneDto.newBuilder()
                                                       .withNumber("123456")
                                                       .withCityCode("11")
                                                       .withCountryCode("54")
                                                       .build()
                                                             ))
                                       .build();
        LocalDateTime creationDate = LocalDateTime.now();
        UUID id = UUID.randomUUID();

        doNothing().when(userValidator).validateCreateUserRequest(any());

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(User.newBuilder().build()));

        assertThrows(ValidationException.class, () -> userService.createUser(userRequestDto));
    }

    @Test
    public void loginOk() throws ValidationException {
        userRequestDto = UserRequestDto.newBuilder()
                                       .withEmail("email@test.com")
                                       .withPassword("Passss.44819")
                                       .build();
        UUID id = UUID.randomUUID();
        LocalDateTime date = LocalDateTime.now();
        String hashPass = EncryptUtil.createHash("email@test.com", "Passss.44819");

        when(userRepository.findByEmail("email@test.com")).thenReturn(Optional.of(User.newBuilder()
                                                                                      .withId(id)
                                                                                      .withLastUpdateDate(date)
                                                                                      .withCreationDate(date)
                                                                                      .withLastLoginDate(date)
                                                                                      .withHashPassword(hashPass)
                                                                                      .withStatus(UserStatus.ENABLED)
                                                                                      .withLastTokenApi("last_token_test")
                                                                                      .withName("pepe")
                                                                                      .withEmail("email@test.com")
                                                                                      .build()));

        when(jwtUtil.validate("last_token_test")).thenReturn(true);

        userResponseDto = userService.login(userRequestDto);

        assertEquals(userResponseDto.getId(), id.toString());
        assertEquals(userResponseDto.getModified(), date.toString());
        assertEquals(userResponseDto.getLastLogin(), date.toString());
        assertEquals(userResponseDto.getModified(), date.toString());
        assertEquals(userResponseDto.getToken(), "last_token_test");
        assertEquals(userResponseDto.getIsActive(), UserStatus.ENABLED.name());
    }

    @Test
    public void loginRefreshTokenOk() throws ValidationException {
        userRequestDto = UserRequestDto.newBuilder()
                                       .withEmail("email@test.com")
                                       .withPassword("Passss.44819")
                                       .build();
        UUID id = UUID.randomUUID();
        LocalDateTime date = LocalDateTime.now();
        String hashPass = EncryptUtil.createHash("email@test.com", "Passss.44819");

        when(userRepository.findByEmail("email@test.com")).thenReturn(Optional.of(User.newBuilder()
                                                                                      .withId(id)
                                                                                      .withLastUpdateDate(date)
                                                                                      .withCreationDate(date)
                                                                                      .withLastLoginDate(date)
                                                                                      .withHashPassword(hashPass)
                                                                                      .withStatus(UserStatus.ENABLED)
                                                                                      .withLastTokenApi("last_token_test")
                                                                                      .withName("pepe")
                                                                                      .withEmail("email@test.com")
                                                                                      .build()));

        when(userRepository.save(any())).thenReturn(User.newBuilder()
                                                                    .withId(id)
                                                                    .withLastUpdateDate(date)
                                                                    .withCreationDate(date)
                                                                    .withLastLoginDate(date)
                                                                    .withHashPassword(hashPass)
                                                                    .withStatus(UserStatus.ENABLED)
                                                                    .withLastTokenApi("token_refresqued")
                                                                    .withName("pepe")
                                                                    .withEmail("email@test.com")
                                                                    .build());

        when(jwtUtil.validate("last_token_test")).thenReturn(false);

        when(jwtUtil.createApiToken(any())).thenReturn("token_refresqued");

        userResponseDto = userService.login(userRequestDto);

        assertEquals(userResponseDto.getId(), id.toString());
        assertEquals(userResponseDto.getModified(), date.toString());
        assertEquals(userResponseDto.getLastLogin(), date.toString());
        assertEquals(userResponseDto.getModified(), date.toString());
        assertEquals(userResponseDto.getToken(), "token_refresqued");
        assertEquals(userResponseDto.getIsActive(), UserStatus.ENABLED.name());
    }

    @Test
    public void loginConbinationFailEmailOk() throws ValidationException {
        userRequestDto = UserRequestDto.newBuilder()
                                       .withEmail("email@test.com")
                                       .withPassword("Passss.44819")
                                       .build();
        UUID id = UUID.randomUUID();
        LocalDateTime date = LocalDateTime.now();
        String hashPass = EncryptUtil.createHash("email@tsest.com", "Passss.44819");

        when(userRepository.findByEmail("email@test.com")).thenReturn(Optional.empty());


        assertThrows(ValidationException.class, () -> userResponseDto = userService.login(userRequestDto));
    }

    @Test
    public void loginPassIncorrect() throws ValidationException {
        userRequestDto = UserRequestDto.newBuilder()
                                       .withEmail("email@test.com")
                                       .withPassword("Passss.44819")
                                       .build();
        UUID id = UUID.randomUUID();
        LocalDateTime date = LocalDateTime.now();
        String hashPass = EncryptUtil.createHash("ema3il@test.com", "Passss.44819");

        when(userRepository.findByEmail("email@test.com")).thenReturn(Optional.of(User.newBuilder()
                                                                                      .withId(id)
                                                                                      .withLastUpdateDate(date)
                                                                                      .withCreationDate(date)
                                                                                      .withLastLoginDate(date)
                                                                                      .withHashPassword(hashPass)
                                                                                      .withStatus(UserStatus.ENABLED)
                                                                                      .withLastTokenApi("last_token_test")
                                                                                      .withName("pepe")
                                                                                      .withEmail("email@test.com")
                                                                                      .build()));

        assertThrows(ValidationException.class, () -> userResponseDto = userService.login(userRequestDto));
    }
}
