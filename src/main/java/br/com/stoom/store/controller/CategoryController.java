package br.com.stoom.store.controller;

import br.com.stoom.store.business.interfaces.ICategoryBO;
import br.com.stoom.store.dto.input.CategoryDataInputDTO;
import br.com.stoom.store.dto.output.CategoryDataOutputDTO;
import br.com.stoom.store.model.Category;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryBO categoryService;

    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<CategoryDataOutputDTO>> findAllCategories() {
        List<Category> categories = categoryService.findAll();

        if (categories.isEmpty())
            return ResponseEntity.notFound().build();

        return ok(categories.stream().map(c -> mapper.map(c, CategoryDataOutputDTO.class)).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<CategoryDataOutputDTO> createCategory(@RequestBody @Valid CategoryDataInputDTO categoryData) {
        Category category = categoryService.save(new Category(categoryData.getName()));

        return created(URI.create("/api/categories/" + category.getId()))
                .body(mapper.map(category, CategoryDataOutputDTO.class));
    }

    @PatchMapping("/{categoryId}/disable")
    public ResponseEntity<Void> disableCategory(@PathVariable Long categoryId) {
        categoryService.toggleCategory(categoryId, false);

        return noContent().build();
    }

    @PatchMapping("/{categoryId}/enable")
    public ResponseEntity<Void> enableCategory(@PathVariable Long categoryId) {
        categoryService.toggleCategory(categoryId, true);

        return noContent().build();
    }

}
