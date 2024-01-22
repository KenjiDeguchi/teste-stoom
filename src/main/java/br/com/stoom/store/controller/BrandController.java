package br.com.stoom.store.controller;

import br.com.stoom.store.business.interfaces.IBrandBO;
import br.com.stoom.store.dto.input.BrandDataInputDTO;
import br.com.stoom.store.dto.output.BrandDataOutputDTO;
import br.com.stoom.store.model.Brand;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final IBrandBO brandService;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<BrandDataOutputDTO> createBrand(@RequestBody @Valid BrandDataInputDTO brandData) {
        Brand brand = brandService.save(new Brand(brandData.getName()));

        return created(URI.create("/api/brands/" + brand.getId()))
                .body(modelMapper.map(brand, BrandDataOutputDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<BrandDataOutputDTO>> findAllBrands() {
        List<Brand> brands = brandService.findAll();

        if (brands.isEmpty())
            return ResponseEntity.notFound().build();

        return ok(brands.stream().map(b -> modelMapper.map(b, BrandDataOutputDTO.class)).collect(Collectors.toList()));
    }

    @PatchMapping("/{brandId}/disable")
    public ResponseEntity<Void> disableBrand(@PathVariable Long brandId) {
        brandService.toggleBrand(brandId, false);

        return noContent().build();
    }

    @PatchMapping("/{brandId}/enable")
    public ResponseEntity<Void> enableBrand(@PathVariable Long brandId) {
        brandService.toggleBrand(brandId, true);

        return noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBrand(@PathVariable Long brandId) {
        brandService.delete(brandId);

        return noContent().build();
    }

}
