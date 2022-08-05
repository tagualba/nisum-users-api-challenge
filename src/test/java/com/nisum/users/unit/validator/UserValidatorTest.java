package com.nisum.users.unit.validator;

import com.nisum.users.exceptions.ValidationException;
import com.nisum.users.model.dtos.PhoneDto;
import com.nisum.users.model.dtos.UserRequestDto;
import com.nisum.users.validators.UserValidator;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserValidatorTest {

    private UserRequestDto userRequestDto;

    private UserValidator userValidator;

    @BeforeEach
    public void init(){
        userValidator = new UserValidator("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?="
            + ".*[@#$%^&+=.])(?=\\S+$).{8,}$");
    }

    @Test
    public void validateRequestCreateUserOk() throws ValidationException {
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

        userValidator.validateCreateUserRequest(userRequestDto);
    }

    @Test
    public void validateRequestCreateUserFailMailRegex() throws ValidationException {
        userRequestDto = UserRequestDto.newBuilder()
                                       .withName("name_test")
                                       .withEmail("email@test .com")
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

        assertThrows(ValidationException.class, () -> userValidator.validateCreateUserRequest(userRequestDto));
    }

    @Test
    public void validateRequestCreateUserFailRegexPass() throws ValidationException {
        userRequestDto = UserRequestDto.newBuilder()
                                       .withName("name_test")
                                       .withEmail("email@test.com")
                                       .withPassword("Pas sss.4 4819")
                                       .withPhones(
                                           Lists.newArrayList(
                                               PhoneDto.newBuilder()
                                                       .withNumber("123456")
                                                       .withCityCode("11")
                                                       .withCountryCode("54")
                                                       .build()
                                                             ))
                                       .build();

        assertThrows(ValidationException.class, () -> userValidator.validateCreateUserRequest(userRequestDto));
    }

    @Test
    public void validateRequestCreateUserMissingFieldFail() throws ValidationException {
        userRequestDto = UserRequestDto.newBuilder()
                                       .withEmail("email@test.com")
                                       .withPassword("Pas sss.4 4819")
                                       .withPhones(
                                           Lists.newArrayList(
                                               PhoneDto.newBuilder()
                                                       .withNumber("123456")
                                                       .withCityCode("11")
                                                       .withCountryCode("54")
                                                       .build()
                                                             ))
                                       .build();

        assertThrows(ValidationException.class, () -> userValidator.validateCreateUserRequest(userRequestDto));
    }

    @Test
    public void validateRequestLoginOk() throws ValidationException {
        userRequestDto = UserRequestDto.newBuilder()
                                       .withEmail("email@test.com")
                                       .withPassword("Passss.44819")
                                       .build();

        userValidator.validateLoginRequest(userRequestDto);
    }

    @Test
    public void validateRequestLoignFailMailRegex() throws ValidationException {
        userRequestDto = UserRequestDto.newBuilder()
                                       .withEmail("email@te st.com")
                                       .withPassword("Passss.44819")
                                       .build();

        assertThrows(ValidationException.class, () -> userValidator.validateLoginRequest(userRequestDto));
    }

    @Test
    public void validateRequestLoignFailPassRegex()throws ValidationException {
            userRequestDto = UserRequestDto.newBuilder()
                                           .withEmail("email@test.com")
                                           .withPassword("Pas sss.4 4819")
                                           .build();

            assertThrows(ValidationException.class, () -> userValidator.validateLoginRequest(userRequestDto));
        }

    @Test
    public void validateRequestLoginMissingFieldFail() throws ValidationException {
        userRequestDto = UserRequestDto.newBuilder()
                                       .withEmail("email@test.com")
                                       .build();

        assertThrows(ValidationException.class, () -> userValidator.validateLoginRequest(userRequestDto));
    }



}
