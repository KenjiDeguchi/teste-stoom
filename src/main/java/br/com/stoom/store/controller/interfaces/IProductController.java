package br.com.stoom.store.controller.interfaces;

import br.com.stoom.store.dto.ProductDto;
import br.com.stoom.store.exception.CategoryNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
@RequestMapping("/api/products")
public interface IProductController {

    @Operation(
            summary = "Get ALL Products REST API",
            description = "Get ALL Products REST API is used to get all products, its return is paginated for easy viewing"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping
    Page<ProductDto> getAllProducts();

    @Operation(
            summary = "Get ALL Active Products REST API",
            description = "Get ALL Active Products REST API is used to get all active products, its return is paginated for easy viewing"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("/active")
    ResponseEntity<Page<ProductDto>> getAllActiveProducts(@RequestParam boolean status);

    @Operation(
            summary = "Get Products by Brand REST API",
            description = "Get Products by Brand REST API is used to get all products by a specific brand, its return is paginated for easy viewing"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("brand/{brandName}")
    ResponseEntity<Page<ProductDto>> getProductsByBrand(@PathVariable String brandName);

    @Operation(
            summary = "Get Products by Category REST API",
            description = "Get Products by Category REST API is used to get all products by a specific category, its return is paginated for easy viewing"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS"),
            @ApiResponse(responseCode = "404", description = "HTTP Status 404 NOT FOUND - Category not found")
    })
    @GetMapping("category/{categoryName}")
    ResponseEntity<Page<ProductDto>> getProductsByCategory(@PathVariable String categoryName);

    @Operation(
            summary = "Create Product REST API",
            description = "Create Product REST API is used to create a new product"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "HTTP Status 201 CREATED - Product created successfully"),
            @ApiResponse(responseCode = "400", description = "HTTP Status 400 BAD REQUEST - Invalid product details")
    })
    @PostMapping
    ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto, UriComponentsBuilder uriBuilder);

    @Operation(
            summary = "Activate Product REST API",
            description = "Activate Product REST API is used to activate a product"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "HTTP Status 204 NO CONTENT - Product activated successfully"),
            @ApiResponse(responseCode = "404", description = "HTTP Status 404 NOT FOUND - Product not found")
    })
    @PutMapping("/activate/{productId}/")
    ResponseEntity<Void> activateProduct(@PathVariable Long productId);

    @Operation(
            summary = "Deactivate Product REST API",
            description = "Deactivate Product REST API is used to deactivate a product"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "HTTP Status 204 NO CONTENT - Product deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "HTTP Status 404 NOT FOUND - Product not found")
    })
    @PutMapping("/deactivate/{productId}")
    ResponseEntity<Void> deactivateProduct(@PathVariable Long productId);

    @Operation(
            summary = "Export Products to Excel REST API",
            description = "Export Products to Excel REST API is used to export all products to an Excel file"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS - Products exported successfully"),
            @ApiResponse(responseCode = "500", description = "HTTP Status 500 INTERNAL SERVER ERROR - Error occurred while exporting products")
    })
    @GetMapping("/export/excel")
    void exportProductsToExcel(@RequestParam boolean active, HttpServletResponse response) throws IOException;

    @Operation(
            summary = "Export Products to PDF REST API",
            description = "Export Products to PDF REST API is used to export all products to a PDF file"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS - Products exported successfully"),
            @ApiResponse(responseCode = "500", description = "HTTP Status 500 INTERNAL SERVER ERROR - Error occurred while exporting products")
    })
    @GetMapping("/export/pdf")
    void exportProductsToPDF(@RequestParam boolean active, HttpServletResponse response);
}
