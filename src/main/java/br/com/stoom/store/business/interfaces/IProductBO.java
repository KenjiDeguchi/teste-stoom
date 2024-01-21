package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.model.Product;

import java.util.List;

public interface IProductBO {

    List<Product> findAll(Long categoryId, Long brandId);

    List<Product> findAllByCategoryId(Long categoryId);

    List<Product> findAllByBrandId(Long brandId);

    Product save(Product product);

    void toggleProduct(Long productId, boolean active);

}
