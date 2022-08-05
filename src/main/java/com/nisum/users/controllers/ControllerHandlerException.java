package com.nisum.users.controllers;

import com.nisum.users.exceptions.ApiError;
import com.nisum.users.exceptions.ValidationException;
import com.nisum.users.statics.ErrorCode;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ControllerHandlerException {
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiError> noHandlerFoundException(HttpServletRequest req, NoHandlerFoundException ex) {
        ApiError apiError =
            new ApiError("ROUTE_NOT_FOUND", String.format("Route %s not found", req.getRequestURI()), HttpStatus.NOT_FOUND.value());

        System.out.println(String.format("Event: %s , %s %s", "noHandlerFoundException", ex.getMessage(), ex));
        System.out.println(String.format("Event: %s, apierror: %s", "noHandlerFoundException", apiError.toString()));

        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler(ValidationException.class)
    public HttpEntity<ApiError> handleValidationException(ValidationException ex) {
        ErrorCode errorCode = ex.getValidationErrorCode();

        ApiError apiError =
            new ApiError(errorCode.getName(), errorCode.getCode(), errorCode.getMessage(), errorCode.getHttpStatus().value());

        System.out.println(String.format("Event: %s , %s %s", "handleValidationException", ex.getMessage(), ex));
        System.out.println(String.format("Event: %s, apierror: %s", "handleValidationException", apiError.toString()));

        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    protected HttpEntity<ApiError> handleUnknownException(Exception ex) {
        ApiError apiError =
            new ApiError("INTERNAL_SERVER_ERROR", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());

        System.out.println(String.format("Event: %s , %s %s", "handleRuntimeException", ex.getMessage(), ex));
        System.out.println(String.format("Event: %s, apierror: %s", "handleValidationException", apiError.toString()));

        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

}
