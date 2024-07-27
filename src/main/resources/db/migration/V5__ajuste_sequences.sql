-- Ajustar a sequência PRODUCT_SEQ para o próximo valor disponível
SELECT setval('PRODUCT_SEQ', (SELECT COALESCE(MAX(id), 0) + 1 FROM products));

-- Ajustar a sequência BRAND_SEQ para o próximo valor disponível
SELECT setval('BRAND_SEQ', (SELECT COALESCE(MAX(id), 0) + 1 FROM brands));

-- Ajustar a sequência CATEGORY_SEQ para o próximo valor disponível
SELECT setval('CATEGORY_SEQ', (SELECT COALESCE(MAX(id), 0) + 1 FROM categories));
