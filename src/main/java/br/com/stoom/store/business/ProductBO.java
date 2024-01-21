package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IProductBO;
import br.com.stoom.store.exceptions.NotFoundException;
import br.com.stoom.store.model.Product;
import br.com.stoom.store.repository.BrandRepository;
import br.com.stoom.store.repository.CategoryRepository;
import br.com.stoom.store.repository.ProductRepository;
import br.com.stoom.store.repository.specifications.ProductSpecifications;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductBO implements IProductBO {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final BrandRepository brandRepository;

    @Override
    public List<Product> findAll(String category, String brand) {
        Specification<Product> specs = Specification
                .where(ProductSpecifications.byCategory(category))
                .and(Specification.where(ProductSpecifications.byBrand(brand)));

        return productRepository.findAll(specs);
    }

    @Override
    public Product save(Product product) {
        if (!categoryRepository.existsById(product.getCategory().getId()))
            throw new NotFoundException("Category not found");

        else if (!brandRepository.existsById(product.brand.getId()))
            throw new NotFoundException("Brand not found");

        return productRepository.save(product);
    }
}
