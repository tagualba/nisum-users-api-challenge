package com.nisum.users.model.domain;

import com.nisum.users.statics.UserStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "users_test")
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "creation_date", updatable = false,  nullable = false)
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "last_update_date", nullable = false)
    private LocalDateTime lastUpdateDate;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "hash_password", nullable = false)
    private String hashPassword;

    @Column(name = "last_login_date", nullable = true)
    private LocalDateTime lastLoginDate;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "last_token_api")
    private String lastTokenApi;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userPhoneFk")
    private List<Phone> phones;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;

    public User(){
    }
    public User(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.hashPassword = builder.hashPassword;
        this.email = builder.email;
        this.phones = builder.phones;
        this.status = builder.status;
        this.creationDate = builder.creationDate;
        this.lastLoginDate = builder.lastLoginDate;
        lastUpdateDate = builder.lastUpdateDate;
        this.lastTokenApi = builder.lastTokenApi;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(User copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.name = copy.getName();
        builder.hashPassword = copy.getHashPassword();
        builder.email = copy.getEmail();
        builder.phones = copy.getPhones();
        builder.status = copy.getStatus();
        builder.lastLoginDate = copy.getLastLoginDate();
        builder.lastUpdateDate = copy.getLastUpdateDate();
        builder.creationDate = copy.getCreationDate();
        builder.lastTokenApi = copy.getLastTokenApi();
        return builder;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public String getEmail() {
        return email;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public UserStatus getStatus() {
        return status;
    }

    public String getLastTokenApi() {
        return lastTokenApi;
    }

    public static final class Builder {

        private UUID id;
        private String name;
        private String hashPassword;
        private String email;
        private List<Phone> phones;
        private UserStatus status;
        private LocalDateTime creationDate;
        private LocalDateTime lastLoginDate;
        private LocalDateTime lastUpdateDate;
        private String lastTokenApi;


        private Builder() {
        }

        public Builder withId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withHashPassword(String hashPassword) {
            this.hashPassword = hashPassword;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPhones(List<Phone> phones) {
            this.phones = phones;
            return this;
        }

        public Builder withStatus(UserStatus status){
            this.status = status;
            return this;
        }

        public Builder withCreationDate(LocalDateTime creationDate){
            this.creationDate = creationDate;
            return this;
        }

        public Builder withLastUpdateDate(LocalDateTime lastUpdateDate){
            this.lastUpdateDate = lastUpdateDate;
            return this;
        }

        public Builder withLastLoginDate(LocalDateTime lastLoginDate){
            this.lastLoginDate = lastLoginDate;
            return this;
        }

        public Builder withLastTokenApi(String lastTokenApi){
            this.lastTokenApi = lastTokenApi;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("name", name)
            .build();
    }

}
