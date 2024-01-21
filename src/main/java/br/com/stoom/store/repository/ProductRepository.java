package br.com.stoom.store.repository;

import br.com.stoom.store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Query("select p " +
           "from Product p " +
           "inner join p.category pc " +
           "where pc.active = true " +
           "and pc.id = :categoryId")
    List<Product> findAllByCategoryIdAndActive(@Param("categoryId") Long categoryId);

    @Query("select p " +
           "from Product p " +
           "inner join p.brand pb " +
           "where pb.active = true " +
           "and pb.id = :brandId")
    List<Product> findAllByBrandIdAndActive(@Param("brandId") Long brandId);
}