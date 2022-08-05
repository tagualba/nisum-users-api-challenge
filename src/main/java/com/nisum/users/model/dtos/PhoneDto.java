package com.nisum.users.model.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonDeserialize(builder = PhoneDto.Builder.class)
public class PhoneDto {
    private String number;

    private String cityCode;

    private String countryCode;

    private PhoneDto(Builder builder){
        number = builder.number;
        cityCode = builder.cityCode;
        countryCode = builder.countryCode;

    }

    public static Builder newBuilder(){return new Builder();}

    public static Builder newBuilder(PhoneDto copy){
        Builder builder = new Builder();
        builder.number = copy.getNumber();
        builder.cityCode = copy.getCityCode();
        builder.countryCode = copy.getCountryCode();;
        return builder;
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

    @JsonPOJOBuilder
    public static final class Builder{
        private String number;

        private String cityCode;

        private String countryCode;


        private Builder(){}

        public Builder withNumber(String number){
            this.number = number;
            return this;
        }

        public Builder withCityCode(String cityCode){
            this.cityCode = cityCode;
            return this;
        }

        public Builder withCountryCode(String countryCode){
            this.countryCode = countryCode;
            return this;
        }

        public PhoneDto build(){return new PhoneDto(this);}
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this)
            .append("number", number.substring(0,4).toString()).build();
    }
}
