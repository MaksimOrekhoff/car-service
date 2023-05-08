package ru.rikmasters.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validationExceptionHandler(MethodArgumentNotValidException e) {
        return new ErrorResponse(e.getLocalizedMessage(), e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse validationExceptionHandler(final NotFoundException e) {
        return new ErrorResponse(e.getMessage(), e.getLocalizedMessage());

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validationExceptionHandler(final MethodArgumentTypeMismatchException e) {
        return new ErrorResponse(e.getMessage(), e.getLocalizedMessage());
    }

    @ExceptionHandler(HttpClientErrorException.MethodNotAllowed.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse validationExceptionHandler(final HttpClientErrorException.MethodNotAllowed e) {
        return new ErrorResponse(e.getMessage(), e.getLocalizedMessage());
    }
    @ExceptionHandler(BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validationExceptionHandler(final BadRequest e) {
        return new ErrorResponse(e.getMessage(), e.getLocalizedMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse validationExceptionHandler(final IllegalArgumentException e) {
        return new ErrorResponse(e.getMessage(), e.getLocalizedMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse validationExceptionHandler(final MissingServletRequestParameterException e) {
        return new ErrorResponse(e.getMessage(), e.getLocalizedMessage());
    }

}
