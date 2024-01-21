package br.com.stoom.store.controller;

import br.com.stoom.store.business.interfaces.IBrandBO;
import br.com.stoom.store.business.interfaces.IProductBO;
import br.com.stoom.store.dto.BrandDataInputDTO;
import br.com.stoom.store.dto.BrandDataOutputDTO;
import br.com.stoom.store.dto.ProductDataOutputDTO;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final IBrandBO brandService;

    private final IProductBO productBO;

    @PostMapping
    public ResponseEntity<BrandDataOutputDTO> createBrand(@RequestBody @Valid BrandDataInputDTO brandData) {
        Brand brand = brandService.save(new Brand(brandData.getName()));

        return ResponseEntity
                .created(URI.create("/api/brands/" + brand.getId()))
                .body(new BrandDataOutputDTO(brand.getId(), brand.getName()));
    }

    @GetMapping
    public ResponseEntity<List<BrandDataOutputDTO>> findAllBrands() {
        List<Brand> brands = brandService.findAll();

        if (brands.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(
                brands
                        .stream()
                        .map(b -> new BrandDataOutputDTO(b.getId(), b.getName()))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{brandId}/products")
    public ResponseEntity<List<ProductDataOutputDTO>> findAllProductsByBrandId(@PathVariable Long brandId) {
        List<Product> products = productBO.findAllByBrandId(brandId);

        if (products.isEmpty())
            return ResponseEntity.notFound().build();

        List<ProductDataOutputDTO> productsResponses = products.stream()
                .map(p -> new ProductDataOutputDTO(
                        p.getId(),
                        p.getSku(),
                        p.getName(),
                        p.getDescription(),
                        p.getPrice(),
                        p.getCategory().getId(),
                        p.getBrand().getId()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(productsResponses);
    }

    @PatchMapping("/{brandId}/disable")
    public ResponseEntity<Void> disableBrand(@PathVariable Long brandId) {
        brandService.toggleBrand(brandId, false);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping("/{brandId}/enable")
    public ResponseEntity<Void> enableBrand(@PathVariable Long brandId) {
        brandService.toggleBrand(brandId, true);

        return ResponseEntity
                .noContent()
                .build();
    }

}
