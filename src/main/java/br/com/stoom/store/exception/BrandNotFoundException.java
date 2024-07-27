package br.com.stoom.store.exception;


import org.springframework.http.HttpStatus;

public class BrandNotFoundException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public BrandNotFoundException() {
        super("brand-4", HttpStatus.NOT_FOUND);
    }
}
