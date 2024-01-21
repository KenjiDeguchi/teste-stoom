package br.com.stoom.store.controller;

import br.com.stoom.store.business.interfaces.IBrandBO;
import br.com.stoom.store.dto.BrandDataInputDTO;
import br.com.stoom.store.dto.BrandDataOutputDTO;
import br.com.stoom.store.model.Brand;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final IBrandBO brandService;

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
