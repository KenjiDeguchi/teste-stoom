package br.com.stoom.store.controller;

import br.com.stoom.store.business.interfaces.ICategoryBO;
import br.com.stoom.store.business.interfaces.IProductBO;
import br.com.stoom.store.dto.CategoryDataInputDTO;
import br.com.stoom.store.dto.CategoryDataOutputDTO;
import br.com.stoom.store.dto.ProductDataOutputDTO;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryBO categoryService;

    private final IProductBO productBO;

    @GetMapping
    public ResponseEntity<List<CategoryDataOutputDTO>> findAllCategories() {
        List<Category> categories = categoryService.findAll();

        if (categories.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(
                categories
                        .stream()
                        .map(c -> new CategoryDataOutputDTO(c.getId(), c.getName())).collect(Collectors.toList())
        );
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<ProductDataOutputDTO>> findAllProductsByCategoryId(@PathVariable Long categoryId) {
        List<Product> products = productBO.findAllByCategoryId(categoryId);

        if (products.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(
                products.stream()
                        .map(
                                p -> new ProductDataOutputDTO(
                                        p.getId(),
                                        p.getSku(),
                                        p.getName(),
                                        p.getDescription(),
                                        p.getPrice(),
                                        p.getCategory().getId(),
                                        p.getBrand().getId()
                                )
                        )
                        .collect(Collectors.toList())
        );
    }

    @PostMapping
    public ResponseEntity<CategoryDataOutputDTO> createCategory(@RequestBody @Valid CategoryDataInputDTO categoryData) {
        Category category = categoryService.save(new Category(categoryData.getName()));

        return ResponseEntity
                .created(URI.create("/api/categories/" + category.getId()))
                .body(new CategoryDataOutputDTO(category.getId(), category.getName()));
    }

    @PatchMapping("/{categoryId}/disable")
    public ResponseEntity<Void> disableCategory(@PathVariable Long categoryId) {
        categoryService.toggleCategory(categoryId, false);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping("/{categoryId}/enable")
    public ResponseEntity<Void> enableCategory(@PathVariable Long categoryId) {
        categoryService.toggleCategory(categoryId, true);

        return ResponseEntity
                .noContent()
                .build();
    }

}
