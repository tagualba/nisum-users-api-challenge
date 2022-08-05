package com.nisum.users.translators;

import com.nisum.users.model.domain.Phone;
import com.nisum.users.model.dtos.PhoneDto;

public class PhoneTranslator {

    public static Phone toDomain(PhoneDto phoneDto){
        return Phone.newBuilder().withNumber(phoneDto.getNumber()).withCityCode(phoneDto.getCityCode()).withCountryCode(phoneDto.getCountryCode()).build();
    }
}
