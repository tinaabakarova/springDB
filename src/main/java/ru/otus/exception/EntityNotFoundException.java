package ru.otus.exception;

class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException() {
    }

    EntityNotFoundException(String message) {
        super(message);
    }
}
