package br.com.stoom.store.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorOutputDTO {
    private int status;
    private String message;
    private List<String> details;
}
