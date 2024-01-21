package br.com.stoom.store.controller;

import br.com.stoom.store.business.interfaces.IBrandBO;
import br.com.stoom.store.business.interfaces.ICategoryBO;
import br.com.stoom.store.business.interfaces.IProductBO;
import br.com.stoom.store.dto.ProductCreatedOutputDTO;
import br.com.stoom.store.dto.ProductDataInputDTO;
import br.com.stoom.store.dto.ProductDataOutputDTO;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Product;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductBO productService;

    private final ICategoryBO categoryService;

    private final IBrandBO brandService;

    @GetMapping
    public ResponseEntity<List<ProductDataOutputDTO>> findAll(
            @RequestParam(required = false) Long category,
            @RequestParam(required = false) Long brand
    ) {
        List<ProductDataOutputDTO> products = productService.findAll(category, brand)
                .stream()
                .map(p -> new ProductDataOutputDTO(
                        p.getId(),
                        p.getSku(),
                        p.getName(),
                        p.getDescription(),
                        p.getPrice(),
                        p.getCategory().getId(),
                        p.getBrand().getId())
                )
                .collect(Collectors.toList());

        if (!products.isEmpty())
            return ResponseEntity.ok(products);
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProductCreatedOutputDTO> createProduct(@RequestBody @Valid ProductDataInputDTO productData) {
        Category category = categoryService.findById(productData.getCategoryId());
        Brand brand = brandService.findById(productData.getBrandId());

        Product product = new Product(
                productData.getSku(), productData.getName(), productData.getDescription(), productData.getPrice());
        product.setCategory(category);
        product.setBrand(brand);

        product = productService.save(product);

        return ResponseEntity
                .created(URI.create("/api/products/" + product.getId()))
                .body(
                        new ProductCreatedOutputDTO
                                (product.getId(), productData.getSku(), productData.getName(), productData.getPrice())
                );
    }

    @PatchMapping("/{productId}/disable")
    public ResponseEntity<Void> disableProduct(@PathVariable Long productId) {
        productService.toggleProduct(productId, false);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping("/{productId}/enable")
    public ResponseEntity<Void> enableProduct(@PathVariable Long productId) {
        productService.toggleProduct(productId, true);

        return ResponseEntity
                .noContent()
                .build();
    }

}
