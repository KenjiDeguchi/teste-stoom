package br.com.stoom.store.dto.output;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductCreatedOutputDTO {
    private Long id;
    private String sku;
    private String name;
    private BigDecimal price;
}
