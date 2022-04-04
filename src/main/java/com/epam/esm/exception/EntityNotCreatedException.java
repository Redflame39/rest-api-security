package com.epam.esm.exception;

public class EntityNotCreatedException extends RuntimeException {

    public EntityNotCreatedException() {
    }

    public EntityNotCreatedException(String message) {
        super(message);
    }

    public EntityNotCreatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotCreatedException(Throwable cause) {
        super(cause);
    }
}
