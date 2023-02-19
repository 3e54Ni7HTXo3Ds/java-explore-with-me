package ru.practicum.ewm.error.exceptions;

import java.util.function.Supplier;

public class ConflictException extends Exception implements Supplier<ConflictException> {
    public ConflictException(String message) {
        super(message);
    }

    @Override
    public ConflictException get() {
        return new ConflictException(getMessage());
    }
}
