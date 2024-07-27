package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.dto.ProductDto;
import br.com.stoom.store.exception.BrandNotFoundException;
import br.com.stoom.store.exception.CategoryNotFoundException;
import br.com.stoom.store.model.Product;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface IProductBO {


    Page<ProductDto> findProduct();

    Page<ProductDto> getAllActiveProducts(boolean status);

    Page<ProductDto> getProductsByBrand(String brandName) throws BrandNotFoundException;

    Page<ProductDto> getProductsByCategory(String categoryName) throws CategoryNotFoundException;

    ProductDto createProduct(ProductDto productDto);

    void activateProduct(Long productId);

    void deactivateProduct(Long productId);

    void exportProductsToExcel(boolean active, HttpServletResponse response) throws IOException;

    void exportProductsToPDF(boolean active, HttpServletResponse response);
}
