package br.com.stoom.store.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BrandDataInputDTO {
    @NotBlank
    private String name;
}
