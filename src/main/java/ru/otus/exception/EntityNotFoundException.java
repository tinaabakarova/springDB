package ru.otus.exception;

public class EntityNotFoundException extends Throwable{
    public EntityNotFoundException() {
    }

    EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(Throwable throwable) {
        super(throwable);
    }
}
