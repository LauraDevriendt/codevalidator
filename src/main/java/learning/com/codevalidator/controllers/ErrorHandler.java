package learning.com.codevalidator.controllers;

import learning.com.codevalidator.Exceptions.NoValidFileException;
import learning.com.codevalidator.models.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.FileNotFoundException;

@ResponseBody
@ControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoValidFileException.class)
    ErrorMessage noValidFileHandler(NoValidFileException e) {
        return new ErrorMessage("400", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FileNotFoundException.class)
    ErrorMessage noValidFileHandler(FileNotFoundException e) {
        return new ErrorMessage("404", e.getMessage());
    }


}


