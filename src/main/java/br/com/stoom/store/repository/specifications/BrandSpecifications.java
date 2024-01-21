package br.com.stoom.store.repository.specifications;

import br.com.stoom.store.model.Brand;
import org.springframework.data.jpa.domain.Specification;

public class BrandSpecifications {
    public static Specification<Brand> active(boolean active) {
        return (root, query, builder) -> builder.equal(root.get("active"), active);
    }
}
