package com.swedbank.StudentApplication;

import com.swedbank.StudentApplication.common.ErrorMessage;
import com.swedbank.StudentApplication.person.exception.PersonException;
import com.swedbank.StudentApplication.person.exception.PersonNotFoundException;
import com.swedbank.StudentApplication.task.exceptiion.TaskNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {

    Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class.getName());

    @ExceptionHandler({PersonNotFoundException.class, TaskNotFoundException.class})
    public ResponseEntity<ErrorMessage> notFoundException(Exception e, WebRequest request){
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), e.getMessage(), request.getDescription(false));
        logger.error(String.valueOf(message));
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception e, WebRequest request){
        ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
