package com.nisum.users.translators;

import com.nisum.users.model.domain.Phone;
import com.nisum.users.model.domain.User;
import com.nisum.users.model.dtos.UserRequestDto;
import com.nisum.users.model.dtos.UserResponseDto;
import com.nisum.users.statics.UserStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UserTranslator {
    public User createUserToDomain(UserRequestDto requestDto, String hashPassword, LocalDateTime creationDate, String lastTokenApi){

        List<Phone> phonesDomain = requestDto.getPhones().stream().map(PhoneTranslator::toDomain).collect(Collectors.toList());

        return User.newBuilder()
                   .withName(requestDto.getName())
                   .withEmail(requestDto.getEmail())
                   .withPhones(phonesDomain)
                   .withStatus(UserStatus.ENABLED)
                   .withHashPassword(hashPassword)
                   .withLastLoginDate(creationDate)
                   .withCreationDate(creationDate)
                   .withLastTokenApi(lastTokenApi)
                   .build();
    }

    public UserResponseDto toResponse(User user){
        return UserResponseDto.newBuilder()
                              .withId(user.getId().toString())
                              .withCreated(user.getCreationDate().toString())
                              .withModified(user.getLastUpdateDate().toString())
                              .withLastLogin(user.getLastLoginDate().toString())
                              .withToken(user.getLastTokenApi())
                              .withIsActive(user.getStatus().name())
                              .build();
    }
}
