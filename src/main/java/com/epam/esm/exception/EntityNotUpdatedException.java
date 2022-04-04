package com.epam.esm.exception;

public class EntityNotUpdatedException extends RuntimeException {

    public EntityNotUpdatedException() {
    }

    public EntityNotUpdatedException(String message) {
        super(message);
    }

    public EntityNotUpdatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotUpdatedException(Throwable cause) {
        super(cause);
    }
}
