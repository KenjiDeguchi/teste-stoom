package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.dto.CategoryDto;
import br.com.stoom.store.dto.ProductDto;
import br.com.stoom.store.exception.BrandNotFoundException;
import br.com.stoom.store.exception.CategoryNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface ICategoryBO {

    List<CategoryDto> getAllActiveCategories();

    void activateCategory(Long categoryId);

    void deactivateCategory(Long categoryId);
}
