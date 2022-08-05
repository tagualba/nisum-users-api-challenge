package com.nisum.users.unit.translators;

import com.nisum.users.model.domain.Phone;
import com.nisum.users.model.domain.User;
import com.nisum.users.model.dtos.PhoneDto;
import com.nisum.users.model.dtos.UserRequestDto;
import com.nisum.users.model.dtos.UserResponseDto;
import com.nisum.users.statics.UserStatus;
import com.nisum.users.translators.UserTranslator;
import java.time.LocalDateTime;
import java.util.UUID;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
public class UserTranslatorTest {
    private UserTranslator userTranslator;

    private UserRequestDto userRequestDto;

    private UserResponseDto userResponseDto;

    private User user;



    @BeforeEach
    public void init(){
        userTranslator = new UserTranslator();
    }

    @Test
    public void createUserToDomainOk(){
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
        user = userTranslator.createUserToDomain(userRequestDto, "hash_pass_test", creationDate, "last_token_api_test");

        assertEquals(user.getName(), userRequestDto.getName());
        assertEquals(user.getEmail(), userRequestDto.getEmail());
        assertEquals(user.getHashPassword(), "hash_pass_test");
        assertEquals(user.getCreationDate(), creationDate);
        assertEquals(user.getLastLoginDate(), creationDate);
        assertEquals(user.getStatus(), UserStatus.ENABLED);
        assertEquals(user.getPhones().get(0).getCityCode(), userRequestDto.getPhones().get(0).getCityCode());
        assertEquals(user.getPhones().get(0).getCountryCode(), userRequestDto.getPhones().get(0).getCountryCode());
        assertEquals(user.getPhones().get(0).getNumber(), userRequestDto.getPhones().get(0).getNumber());
    }

    @Test
    public void toResponseOk(){
        LocalDateTime creationDate = LocalDateTime.now();
        UUID id = UUID.randomUUID();
        user = User.newBuilder()
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
                                       .build();
        userResponseDto = userTranslator.toResponse(user);

        assertEquals(userResponseDto.getId(), id.toString());
        assertEquals(userResponseDto.getCreated(), creationDate.toString());
        assertEquals(userResponseDto.getLastLogin(), creationDate.toString());
        assertEquals(userResponseDto.getIsActive(), UserStatus.ENABLED.name());
        assertEquals(userResponseDto.getToken(), "last_token_test");
    }
}
