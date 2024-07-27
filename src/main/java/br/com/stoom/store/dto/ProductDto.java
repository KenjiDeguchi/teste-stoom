package br.com.stoom.store.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private Long categoryId; // Apenas o ID da categoria
    private Long brandId; // Apenas o ID da marca
    private BigDecimal price;
    private Boolean active;
}
