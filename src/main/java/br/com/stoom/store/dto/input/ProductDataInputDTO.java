package br.com.stoom.store.dto.input;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.Data;
import lombok.NonNull;

@Data
public class ProductDataInputDTO {
    @NotBlank
    private String sku;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Positive
    @NonNull
    private BigDecimal price;

    @Positive
    @NonNull
    private Long categoryId;

    @Positive
    @NonNull
    private Long brandId;
}
