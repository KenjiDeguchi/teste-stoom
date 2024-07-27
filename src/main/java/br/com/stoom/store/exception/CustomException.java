package br.com.stoom.store.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends BusinessException {

    private static final long serialVersionUID = 5553707156721755357L;

    public CustomException(String code, String message, HttpStatus status) {
        super(code, status);
        this.message = message;
    }

    // Adicionar um construtor que utiliza o código e mensagem
    public CustomException(String code, HttpStatus status) {
        super(code, status);
        this.message = null;
    }

    @Override
    public String getMessage() {
        return message;
    }

    private final String message;
}
