package br.com.stoom.store.exception;

import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends BusinessException {

    private static final long serialVersionUID = 5553707156721755355L;

    public CategoryNotFoundException() {
        super("category-6", HttpStatus.NOT_FOUND);
    }
}
