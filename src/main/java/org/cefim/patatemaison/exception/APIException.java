package org.cefim.patatemaison.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class APIException extends Exception {

    private final HttpStatus httpStatus;
    public APIException(String message, HttpStatus httpStatus) {
        super(message);

        this.httpStatus = httpStatus;
    }
}
