package com.nisum.users.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonDeserialize(builder = UserRequestDto.Builder.class)
public class UserResponseDto {

    private String id;

    private String created;

    @JsonProperty("last_login")
    private String lastLogin;

    private String modified;

    @JsonProperty("isactive")
    private String isActive;

    private String token;


    private UserResponseDto(Builder builder){
        id = builder.id;
        created = builder.created;
        modified = builder.modified;
        lastLogin = builder.lastLogin;
        token = builder.token;
        isActive = builder.isActive;
    }

    public static Builder newBuilder(){return new Builder();}

    public static Builder newBuilder(UserResponseDto copy){
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.created = copy.getCreated();
        builder.modified = copy.getModified();
        builder.lastLogin = copy.getLastLogin();
        builder.token = copy.getToken();
        builder.isActive = copy.getIsActive();
        return builder;
    }

    public String getId() {
        return id;
    }

    public String getCreated() {
        return created;
    }

    public String getModified() {
        return modified;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public String getToken() {
        return token;
    }

    public String getIsActive() {
        return isActive;
    }

    @JsonPOJOBuilder
    public static final class Builder{
        private String id;

        private String created;

        private String modified;

        private String lastLogin;

        private String token;

        private String isActive;

        private Builder(){}

        public Builder withId(String id){
            this.id = id;
            return this;
        }

        public Builder withCreated(String created){
            this.created = created;
            return this;
        }

        public Builder withModified(String modified){
            this.modified = modified;
            return this;
        }

        public Builder withLastLogin(String lastLogin){
            this.lastLogin = lastLogin;
            return this;
        }

        public Builder withToken(String token){
            this.token = token;
            return this;
        }

        public Builder withIsActive(String isActive){
            this.isActive = isActive;
            return this;
        }

        public UserResponseDto build(){return new UserResponseDto(this);}
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this)
            .append("id", id)
            .append("created", created)
            .append("modified", modified)
            .append("lastLogin", lastLogin)
            .append("isActive", isActive).build();
    }
}

