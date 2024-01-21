package br.com.stoom.store.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductCreatedOutputDTO {
    private Long id;
    private String sku;
    private String name;
    private BigDecimal price;
}
