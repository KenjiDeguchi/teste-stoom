package br.com.stoom.store.exceptions;

import java.util.List;

public class BadRequestException extends BusinessException {
    public BadRequestException(String message, List<String> details) {
        super(message, 400, details);
    }

    public BadRequestException(String message) {
        super(message, 400, null);
    }
}
