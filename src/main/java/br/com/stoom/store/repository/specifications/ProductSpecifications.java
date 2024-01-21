package br.com.stoom.store.repository.specifications;

import br.com.stoom.store.model.Brand;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class ProductSpecifications {
    public static Specification<Product> byCategory(String category) {
        if (category == null)
            return null;

        return (root, query, builder) -> {
            Join<Product, Category> productCategory = root.join("category");

            return builder.like(productCategory.get("name"), category);
        };
    }

    public static Specification<Product> byCategoryId(Long categoryId) {
        if (categoryId == null)
            return null;

        return (root, query, builder) -> {
            Join<Product, Category> productCategory = root.join("category");

            return builder.equal(productCategory.get("id"), categoryId);
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

    public static Specification<Product> byBrandId(Long brandId) {
        if (brandId == null)
            return null;

        return (root, query, builder) -> {
            Join<Product, Brand> productBrand = root.join("brand");

            return builder.equal(productBrand.get("id"), brandId);
        };
    }

    public static Specification<Product> activeBrand() {
        return  (root, query, builder) -> {
            Join<Product, Brand> productBrand = root.join("brand");

            return builder.equal(productBrand.get("active"), true);
        };
    }

    public static Specification<Product> activeCategory() {
        return (root, query, builder) -> {
            Join<Product, Category> productCategory = root.join("category");

            return builder.equal(productCategory.get("active"), true);
        };
    }
}
