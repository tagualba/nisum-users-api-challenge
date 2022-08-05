package com.nisum.users.unit.translators;

import com.nisum.users.model.domain.Phone;
import com.nisum.users.model.domain.User;
import com.nisum.users.model.dtos.PhoneDto;
import com.nisum.users.model.dtos.UserRequestDto;
import com.nisum.users.model.dtos.UserResponseDto;
import com.nisum.users.translators.PhoneTranslator;
import com.nisum.users.translators.UserTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PhoneTranslatorTest {

    private PhoneDto phoneDto;

    private Phone phone;

    @Test
    public void toDomainOk(){
        phoneDto = PhoneDto.newBuilder().withNumber("12345").withCountryCode("54").withCityCode("11").build();
        phone = PhoneTranslator.toDomain(phoneDto);

        assertEquals(phone.getNumber(), phoneDto.getNumber());
        assertEquals(phone.getCountryCode(), phoneDto.getCountryCode());
        assertEquals(phone.getCityCode(), phoneDto.getCityCode());

    }
}
