package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IProductBO;
import br.com.stoom.store.exceptions.NotFoundException;
import br.com.stoom.store.model.Product;
import br.com.stoom.store.repository.BrandRepository;
import br.com.stoom.store.repository.CategoryRepository;
import br.com.stoom.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.stoom.store.repository.specifications.ProductSpecifications.activeBrand;
import static br.com.stoom.store.repository.specifications.ProductSpecifications.activeCategory;
import static br.com.stoom.store.repository.specifications.ProductSpecifications.activeProduct;
import static br.com.stoom.store.repository.specifications.ProductSpecifications.byBrandId;
import static br.com.stoom.store.repository.specifications.ProductSpecifications.byCategoryId;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
@RequiredArgsConstructor
public class ProductBO implements IProductBO {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final BrandRepository brandRepository;

    @Override
    public List<Product> findAll(Long categoryId, Long brandId) {
        Specification<Product> specs = where(byCategoryId(categoryId))
                .and(byBrandId(brandId))
                .and(activeProduct())
                .and(activeBrand())
                .and(activeCategory());

        return productRepository.findAll(specs);
    }

    @Override
    public List<Product> findAllByCategoryId(Long categoryId) {
        if (!categoryRepository.existsById(categoryId))
            throw new NotFoundException("Category not found");

        return productRepository.findAllByCategoryIdAndActive(categoryId);
    }

    @Override
    public List<Product> findAllByBrandId(Long brandId) {
        if (!brandRepository.existsById(brandId))
            throw new NotFoundException("Brand not found");

        return productRepository.findAllByBrandIdAndActive(brandId);
    }

    @Override
    public Product save(Product product) {
        if (!categoryRepository.existsById(product.getCategory().getId()))
            throw new NotFoundException("Category not found");

        else if (!brandRepository.existsById(product.getBrand().getId()))
            throw new NotFoundException("Brand not found");

        return productRepository.save(product);
    }

    @Override
    public void toggleProduct(Long productId, boolean active) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        product.setActive(active);

        productRepository.save(product);
    }

}
