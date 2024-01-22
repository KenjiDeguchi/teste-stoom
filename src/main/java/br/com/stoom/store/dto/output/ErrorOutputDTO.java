package br.com.stoom.store.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ErrorOutputDTO {
    private int status;
    private String message;
    private List<String> details;
}
