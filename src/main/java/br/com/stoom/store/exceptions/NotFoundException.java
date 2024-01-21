package br.com.stoom.store.exceptions;

import java.util.List;

public class NotFoundException extends BusinessException {
    public NotFoundException(String message, List<String> details) {
        super(message, 404, details);
    }

    public NotFoundException(String message) {
        super(message, 404, null);
    }
}
