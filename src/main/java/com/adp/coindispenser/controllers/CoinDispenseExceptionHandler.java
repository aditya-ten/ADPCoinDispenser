package com.adp.coindispenser.controllers;

import com.adp.coindispenser.dto.CoinDispenseAPIError;
import com.adp.coindispenser.exceptions.OutOfCoinsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class CoinDispenseExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<Object> handleBadRequest(Exception ex) {
        return new ResponseEntity<Object>(CoinDispenseAPIError.builder()
                .errors(List.of("Bad Request"))
                .message(ex.getLocalizedMessage())
                .status(HttpStatus.BAD_REQUEST).build(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OutOfCoinsException.class)
    @ResponseBody
    public ResponseEntity<Object> handleOutOfCoinsException(OutOfCoinsException ex) {
        return new ResponseEntity<Object>(CoinDispenseAPIError.builder()
                .errors(List.of("Insufficient coins to process transaction"))
                .message(ex.getLocalizedMessage())
                .status(HttpStatus.OK).build(), new HttpHeaders(), HttpStatus.OK);
    }

}
