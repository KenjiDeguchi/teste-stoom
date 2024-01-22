package br.com.stoom.store.dto.output;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDataOutputDTO {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private Long brandId;
}
