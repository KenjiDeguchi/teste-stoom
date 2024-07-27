ALTER TABLE products
    ADD CONSTRAINT fk_category
        FOREIGN KEY (category_id) REFERENCES categories (id);

ALTER TABLE products
    ADD CONSTRAINT fk_brand
        FOREIGN KEY (brand_id) REFERENCES brands (id);