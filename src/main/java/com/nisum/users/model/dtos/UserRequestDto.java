package com.nisum.users.model.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonDeserialize(builder = UserRequestDto.Builder.class)
public class UserRequestDto {

    private String name;

    private String email;

    private String password;

    private List<PhoneDto> phones;

    private UserRequestDto(Builder builder){
        name = builder.name;
        email = builder.email;
        password = builder.password;
        phones = builder.phones;
    }

    public static Builder newBuilder(){return new Builder();}

    public static Builder newBuilder(UserRequestDto copy){
        Builder builder = new Builder();
        builder.name = copy.getName();
        builder.email = copy.getEmail();
        builder.password = copy.getPassword();
        builder.phones = copy.getPhones();
        return builder;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<PhoneDto> getPhones() {
        return phones;
    }

    @JsonPOJOBuilder
    public static final class Builder{
        private String name;

        private String email;

        private String password;

        private List<PhoneDto> phones;

        private Builder(){}

        public Builder withName(String name){
            this.name = name;
            return this;
        }

            public Builder withPhones(List<PhoneDto> phones){
            this.phones = phones;
            return this;
        }

        public Builder withEmail(String email){
            this.email = email;
            return this;
        }
        public Builder withPassword(String password){
            this.password = password;
            return this;
        }



        public UserRequestDto build(){return new UserRequestDto(this);}
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this)
            .append("name", name)
            .append("email", email).build();
    }
}
