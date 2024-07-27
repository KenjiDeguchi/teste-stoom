package br.com.stoom.store.repository;

import br.com.stoom.store.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByActive(boolean active, Pageable pageable);

    Page<Product> findByBrandNameIgnoreCaseAndActiveTrue(String brandName, Pageable pageable);

    Page<Product> findByCategoryNameIgnoreCaseAndActiveTrue(String categoryName, Pageable pageable);


}
