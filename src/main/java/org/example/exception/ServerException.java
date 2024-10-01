package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerException extends Exception{
    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException() {
        super();
    }
}
