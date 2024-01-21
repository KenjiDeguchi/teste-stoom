package br.com.stoom.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BrandDataOutputDTO {
    private Long id;
    private String name;
}
