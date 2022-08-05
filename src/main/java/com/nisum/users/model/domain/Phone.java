package com.nisum.users.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "phones_test")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "city_code", nullable = false)
    private String cityCode;

    @Column(name = "country_code", nullable = false)
    private String countryCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userPhoneFk")
    @JsonIgnore
    private User userPhoneFk;

    public Phone(){
    }
    public Phone(Builder builder){
        this.id = builder.id;
        this.number = builder.number;
        this.cityCode = builder.cityCode;
        this.countryCode = builder.countryCode;
        this.userPhoneFk = builder.user;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(Phone copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.number = copy.getNumber();
        builder.cityCode = copy.getCityCode();
        builder.countryCode = copy.getCountryCode();
        builder.user = copy.getUserPhoneFk();
        return builder;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public User getUserPhoneFk() {
        return userPhoneFk;
    }

    public static final class Builder {

        private Long id;
        private String number;
        private String cityCode;
        private String countryCode;
        private User user;

        private Builder() {
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withNumber(String number) {
            this.number = number;
            return this;
        }

        public Builder withCityCode(String cityCode) {
            this.cityCode = cityCode;
            return this;
        }

        public Builder withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public Phone build() {
            return new Phone(this);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .build();
    }

}
