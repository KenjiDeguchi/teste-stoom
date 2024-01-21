package br.com.stoom.store.repository;

import br.com.stoom.store.model.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByActive(boolean active);

    boolean existsByName(String name);
}
