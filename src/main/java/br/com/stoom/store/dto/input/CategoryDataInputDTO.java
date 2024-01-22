package br.com.stoom.store.dto.input;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDataInputDTO {
    @NotBlank
    private String name;
}