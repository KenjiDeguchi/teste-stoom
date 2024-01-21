package br.com.stoom.store.controller;

import br.com.stoom.store.business.interfaces.ICategoryBO;
import br.com.stoom.store.dto.BrandDataOutputDTO;
import br.com.stoom.store.dto.CategoryDataInputDTO;
import br.com.stoom.store.dto.CategoryDataOutputDTO;
import br.com.stoom.store.model.Category;
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
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryBO categoryService;

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
