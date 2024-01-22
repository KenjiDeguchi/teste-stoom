package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.model.Category;
import java.util.List;

public interface ICategoryBO {

    List<Category> findAll();

    Category findById(Long id);

    Category save(Category category);

    void toggleCategory(Long id, boolean active);

    void delete(Long id);

}
