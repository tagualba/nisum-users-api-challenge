package com.nisum.users.exceptions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonDeserialize(builder = ApiError.Builder.class)
public class ApiError {

    private String error;
    private int errorCode;
    private String message;
    private Integer status;
    public ApiError() {
    }

    public ApiError(final String error, final String message, final Integer status) {
        this.error = error;
        this.message = message;
        this.status = status;
    }

    public ApiError(final String error,final int errorCode, final String message, final Integer status) {
        this.error = error;
        this.errorCode = errorCode;
        this.message = message;
        this.status = status;
    }

    private ApiError(final Builder builder) {
        error = builder.error;
        errorCode = builder.errorCode;
        message = builder.message;
        status = builder.status;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(final ApiError copy) {
        Builder builder = new Builder();
        builder.error = copy.getError();
        builder.errorCode = copy.getErrorCode();
        builder.message = copy.getMessage();
        builder.status = copy.getStatus();
        return builder;
    }
    public String getError() {
        return error;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public Integer getStatus() {
        return status;
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("error", error)
            .append("errorCode", errorCode)
            .append("message", message)
            .append("status", status)
            .toString();
    }

    @JsonPOJOBuilder
    public static final class Builder {

        private String error;
        private int errorCode;
        private String message;
        private Integer status;

        private Builder() {
        }

        public Builder withError(final String error) {
            this.error = error;
            return this;
        }

        public Builder withErrorCode(final int errorCode){
            this.errorCode = errorCode;
            return this;
        }

        public Builder withMessage(final String message) {
            this.message = message;
            return this;
        }

        public Builder withStatus(final Integer status) {
            this.status = status;
            return this;
        }

        public ApiError build() {
            return new ApiError(this);
        }
    }
}
