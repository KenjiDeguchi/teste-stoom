package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.ICategoryBO;
import br.com.stoom.store.dto.CategoryDto;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.stoom.store.exception.CategoryNotFoundException;

import java.util.List;

@Service
public class CategoryBO implements ICategoryBO {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<CategoryDto> getAllActiveCategories() {
        List<Category> categories = categoryRepository.findByActiveTrue();

        if (categories.isEmpty()) {
            throw new CategoryNotFoundException();
        }
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .toList();
    }

    @Override
    public void activateCategory(Long categoryId) {
        Category category = getCategory(categoryId);
        category.setActive(true);
        categoryRepository.save(category);
    }

    @Override
    public void deactivateCategory(Long categoryId) {
        Category category = getCategory(categoryId);
        category.setActive(false);
        categoryRepository.save(category);
    }

    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);
    }
}
