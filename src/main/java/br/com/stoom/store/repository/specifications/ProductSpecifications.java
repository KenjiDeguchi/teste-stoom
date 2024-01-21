package br.com.stoom.store.repository.specifications;

import br.com.stoom.store.model.Brand;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Product;
import javax.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {
    public static Specification<Product> byCategory(String category) {
        if (category == null)
            return null;

        return (root, query, builder) -> {
            Join<Product, Category> productCategory = root.join("category");

            return builder.like(productCategory.get("name"), category);
        };
    }

    public static Specification<Product> byBrand(String brand) {
        if (brand == null)
            return null;

        return (root, query, builder) -> {
            Join<Product, Brand> productBrand = root.join("brand");

            return builder.like(productBrand.get("name"), brand);
        };
    }
}
