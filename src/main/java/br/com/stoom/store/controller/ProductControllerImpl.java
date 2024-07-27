package br.com.stoom.store.controller;

import br.com.stoom.store.business.interfaces.IProductBO;
import br.com.stoom.store.controller.interfaces.IProductController;
import br.com.stoom.store.dto.ProductDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RestController
public class ProductControllerImpl implements IProductController {

    @Autowired
    private IProductBO productService;

    @Override
    public Page<ProductDto> getAllProducts() {
        return productService.findProduct();

    }

    @Override
    public ResponseEntity<Page<ProductDto>> getAllActiveProducts(boolean status) {
        Page<ProductDto> products = productService.getAllActiveProducts(status);
        if (products.hasContent()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Page<ProductDto>> getProductsByBrand(@PathVariable String brandName) {
        Page<ProductDto> products = productService.getProductsByBrand(brandName);
        if (products.hasContent()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Page<ProductDto>> getProductsByCategory(@PathVariable String categoryName) {
        Page<ProductDto> products = productService.getProductsByCategory(categoryName);
        if (products.hasContent()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto, UriComponentsBuilder uriBuilder) {
        ProductDto product = productService.createProduct(productDto);
        URI endereco = uriBuilder.path("/api/products/{id}").buildAndExpand(product.getId()).toUri();

        return ResponseEntity.created(endereco).body(product);
    }

    @Override
    public ResponseEntity<Void> activateProduct(@PathVariable Long productId) {
        productService.activateProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> deactivateProduct(@PathVariable Long productId) {
        productService.deactivateProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public void exportProductsToExcel(@RequestParam boolean active, HttpServletResponse response) throws IOException {
        productService.exportProductsToExcel(active, response);
    }

    @Override
    public void exportProductsToPDF(@RequestParam boolean active, HttpServletResponse response) {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=products.pdf");
        productService.exportProductsToPDF(active, response);
    }
}
