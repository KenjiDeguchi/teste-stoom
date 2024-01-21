package br.com.stoom.store.repository;

import br.com.stoom.store.model.Brand;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findAllByActive(boolean active);

    boolean existsByName(String name);
}
