CREATE TABLE products
(
    id          SERIAL PRIMARY KEY,
    sku         VARCHAR(50)    NOT NULL,
    name        VARCHAR(100)   NOT NULL,
    description TEXT,
    category_id BIGINT,
    brand_id    BIGINT,
    price       DECIMAL(10, 2) NOT NULL,
    active      BOOLEAN        NOT NULL
);

CREATE TABLE categories
(
    id     SERIAL PRIMARY KEY,
    name   VARCHAR(255) NOT NULL,
    active BOOLEAN      NOT NULL
);

CREATE TABLE brands
(
    id     SERIAL PRIMARY KEY,
    name   VARCHAR(100) NOT NULL,
    active BOOLEAN      NOT NULL
);