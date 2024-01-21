package br.com.stoom.store.exceptions;

import java.util.List;
import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {
    private final int status;
    private final List<String> details;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public BusinessException(String message, int status, List<String> details) {
        super(message);
        this.status = status;
        this.details = details;
    }
}
