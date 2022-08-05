package com.nisum.users.validators;

import com.nisum.users.exceptions.ValidationException;
import com.nisum.users.model.dtos.UserRequestDto;
import com.nisum.users.statics.ErrorCode;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class UserValidator {

    private String regexEmail;

    private String regexPassword;

    public UserValidator(@Value("${regex.email}") String regexEmail, @Value("${regex.password}") String regexPassword){
        this.regexEmail = regexEmail;
        this.regexPassword = regexPassword;
    }

    public void validateCreateUserRequest(UserRequestDto request) throws ValidationException {
        StringBuilder propertiesFailMessage = new StringBuilder();

        if (StringUtils.isEmpty(request.getName())) {
            propertiesFailMessage.append(missingFieldDescription("name"));
        }

        if (StringUtils.isEmpty(request.getEmail())) {
            propertiesFailMessage.append(missingFieldDescription("email"));
        }

        if (StringUtils.isEmpty(request.getPassword())) {
            propertiesFailMessage.append(missingFieldDescription("password"));
        }

        if (CollectionUtils.isEmpty(request.getPhones())) {
            propertiesFailMessage.append(missingFieldDescription("phones"));
        }

        if(!validateRegex(request.getEmail(), regexEmail)){
            propertiesFailMessage.append(regexFail("email"));
        }

        if(!validateRegex(request.getPassword(), regexPassword)){
            propertiesFailMessage.append(regexFail("password"));
        }

        if (StringUtils.isNoneBlank(propertiesFailMessage)) {
            String exceptionMessage = propertiesFailMessage.substring(0, propertiesFailMessage.length() - 2);
            throw new ValidationException(ErrorCode.INVALID_DATA, exceptionMessage);
        }

    }

    public void validateLoginRequest(UserRequestDto request) throws ValidationException {
        StringBuilder propertiesFailMessage = new StringBuilder();

        if (StringUtils.isEmpty(request.getEmail())) {
            propertiesFailMessage.append(missingFieldDescription("name"));
        }

        if (StringUtils.isEmpty(request.getPassword())) {
            propertiesFailMessage.append(missingFieldDescription("email"));
        }

        if(!validateRegex(request.getEmail(), regexEmail)){
            propertiesFailMessage.append(regexFail("email"));
        }

        if(!validateRegex(request.getPassword(), regexPassword)){
            propertiesFailMessage.append(regexFail("password"));
        }

        if (StringUtils.isNoneBlank(propertiesFailMessage)) {
            String exceptionMessage = propertiesFailMessage.substring(0, propertiesFailMessage.length() - 2);
            throw new ValidationException(ErrorCode.INVALID_DATA, exceptionMessage);
        }
    }

    private boolean validateRegex(String value, String regex){
        if(Strings.isEmpty(value) || Strings.isEmpty( regex)){
            return false;
        }

        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(value).matches();
    }

    private static String regexFail(String field) {
        return String.format("missing format in %s, ", field);
    }

    private static String missingFieldDescription(String field) {
        return String.format("missing field: %s, ", field);
    }
}
