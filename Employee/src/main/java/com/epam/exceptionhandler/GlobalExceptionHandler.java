package com.epam.exceptionhandler;

import com.epam.dto.ErrorResponse;
import com.epam.exception.DuplicateDataException;
import com.epam.exception.NotFoundException;
import com.epam.exception.ParameterNotCorrectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    ErrorResponse errorResponse;

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler({DuplicateDataException.class, ParameterNotCorrectException.class})
    public ErrorResponse duplicateDataException(Exception ex) {
        errorResponse.setErrormessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_ACCEPTABLE.name());
        errorResponse.setTimestamp(new Date().toString());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse duplicateDataException(NotFoundException ex) {
        errorResponse.setErrormessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.name());
        errorResponse.setTimestamp(new Date().toString());
        return errorResponse;
    }

}
