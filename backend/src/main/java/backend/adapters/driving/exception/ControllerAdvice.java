package backend.adapters.driving.exception;

import backend.adapters.driving.controller.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice( assignableTypes = {
        AppointmentController.class,
        QuestionController.class,
        ReviewController.class,
        TherapyTypeController.class,
        UserController.class
})
public class ControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> resourceNotFoundExceptionHandler(
            ResourceNotFoundException resourceNotFoundException
    ) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", resourceNotFoundException.getMessage());
        return errorMap;
    }

    @ExceptionHandler(DataTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public Map<String, String> dataTypeNotSupportedExceptionHandler(
            DataTypeNotSupportedException dataTypeNotSupportedException
    ) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", dataTypeNotSupportedException.getMessage());
        return errorMap;
    }
}
