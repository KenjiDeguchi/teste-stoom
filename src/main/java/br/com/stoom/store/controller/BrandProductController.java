package br.com.stoom.store.controller;

import br.com.stoom.store.business.interfaces.IProductBO;
import br.com.stoom.store.dto.output.ProductDataOutputDTO;
import br.com.stoom.store.model.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/brands/{brandId}/products")
@RequiredArgsConstructor
public class BrandProductController {

    private final IProductBO productService;

    private final ModelMapper mapper;

    @GetMapping
    private ResponseEntity<List<ProductDataOutputDTO>> findAllProductsByBrand(@PathVariable Long brandId) {
        List<Product> products = productService.findAllByBrandId(brandId);

        if (products.isEmpty())
            return notFound().build();

        return ok(products.stream().map(p -> mapper.map(p, ProductDataOutputDTO.class)).collect(Collectors.toList()));
    }

}
