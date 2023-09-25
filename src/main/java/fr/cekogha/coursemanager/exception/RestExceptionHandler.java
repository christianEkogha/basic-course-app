package fr.cekogha.coursemanager.exception;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;


@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ApiResponse(responseCode = "404", description = "Resource not found")
    public ResponseEntity<ErrorResponse> handleNotFoundException(final NotFoundException exception) {
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setException(exception.getClass().getSimpleName());
        errorResponse.setMessage(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(NotSentException.class)
    @ApiResponse(responseCode = "500", description = "Unable to send message to the kafka topic")
    public ResponseEntity<ErrorResponse> handleNotSentException(final NotFoundException exception) {
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setException(exception.getClass().getSimpleName());
        errorResponse.setMessage(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ApiResponse(responseCode = "400", description = "business exception - invalid argument")
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            final ConstraintViolationException exception) {
        final Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        final List<FieldError> fieldErrors = violations
                .stream()
                .map(violation -> {
                    final FieldError fieldError = new FieldError();
                    fieldError.setErrorMessage(violation.getMessageTemplate());
                    fieldError.setField(violation.getPropertyPath() + " => " + violation.getRootBeanClass());
                    return fieldError;
                })
                .toList();
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setException(exception.getClass().getSimpleName());
        errorResponse.setFieldErrors(fieldErrors);
        errorResponse.setMessage("Paramètres incorrectes");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ApiResponse(responseCode = "400", description = "business exception - invalid argument")
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException exception) {
        final BindingResult bindingResult = exception.getBindingResult();
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors()
                .stream()
                .map(error -> {
                    final FieldError fieldError = new FieldError();
                    fieldError.setErrorMessage(error.getDefaultMessage());
                    fieldError.setField(error.getField() + " => " + error.getCode());
                    return fieldError;
                })
                .toList();
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setException(exception.getClass().getSimpleName());
        errorResponse.setFieldErrors(fieldErrors);
        errorResponse.setMessage("Paramètres incorrectes");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatus(
            final ResponseStatusException exception) {
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(exception.getStatusCode().value());
        errorResponse.setException(exception.getClass().getSimpleName());
        errorResponse.setMessage(exception.getMessage());
        return new ResponseEntity<>(errorResponse, exception.getStatusCode());
    }

    @ExceptionHandler(Throwable.class)
    @ApiResponse(responseCode = "500", description = "Technical exception - default error")
    public ResponseEntity<ErrorResponse> handleThrowable(final Throwable exception) {
        exception.printStackTrace();
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setException(exception.getClass().getSimpleName());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
