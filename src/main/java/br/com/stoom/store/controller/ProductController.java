package br.com.stoom.store.controller;

import br.com.stoom.store.business.interfaces.IBrandBO;
import br.com.stoom.store.business.interfaces.ICategoryBO;
import br.com.stoom.store.business.interfaces.IProductBO;
import br.com.stoom.store.dto.input.ProductDataInputDTO;
import br.com.stoom.store.dto.output.ProductCreatedOutputDTO;
import br.com.stoom.store.dto.output.ProductDataOutputDTO;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductBO productService;

    private final ICategoryBO categoryService;

    private final IBrandBO brandService;

    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ProductDataOutputDTO>> findAll(
            @RequestParam(required = false) Long category,
            @RequestParam(required = false) Long brand
    ) {
        List<Product> products = productService.findAll(category, brand);

        if (products.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(products.stream().map(p -> modelMapper.map(p, ProductDataOutputDTO.class)).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<ProductCreatedOutputDTO> createProduct(@RequestBody @Valid ProductDataInputDTO productData) {
        Category category = categoryService.findById(productData.getCategoryId());
        Brand brand = brandService.findById(productData.getBrandId());

        Product product = productService.save(
                new Product(
                        productData.getSku(),
                        productData.getName(),
                        productData.getDescription(),
                        productData.getPrice(),
                        category,
                        brand
                )
        );

        return ResponseEntity.created(URI.create("/api/products/" + product.getId()))
                .body(modelMapper.map(product, ProductCreatedOutputDTO.class));
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
