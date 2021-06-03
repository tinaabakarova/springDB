package ru.otus.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException() {
    }

    EntityNotFoundException(String message) {
        super(message);
    }
}
