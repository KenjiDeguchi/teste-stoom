package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IBrandBO;
import br.com.stoom.store.business.interfaces.ICategoryBO;
import br.com.stoom.store.business.interfaces.IProductBO;
import br.com.stoom.store.dto.BrandDto;
import br.com.stoom.store.dto.CategoryDto;
import br.com.stoom.store.dto.ProductDto;
import br.com.stoom.store.exception.BrandNotFoundException;
import br.com.stoom.store.exception.CategoryNotFoundException;
import br.com.stoom.store.exception.CustomException;
import br.com.stoom.store.exception.ProductNotFoundException;
import br.com.stoom.store.export.ProductExcelExporter;
import br.com.stoom.store.export.ProductPdfExporter;
import br.com.stoom.store.model.Product;
import br.com.stoom.store.repository.ProductRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductBO implements IProductBO {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductExcelExporter excelExporter;

    @Autowired
    private ProductPdfExporter pdfExporter;

    @Autowired
    private ICategoryBO categoryService;

    @Autowired
    private IBrandBO brandService;

    @Override
    public Page<ProductDto> findProduct() {
        return productRepository
                .findAll(Pageable.unpaged())
                .map(p -> modelMapper.map(p, ProductDto.class));
    }

    @Override
    public Page<ProductDto> getAllActiveProducts(boolean status) {
        Page<Product> products = productRepository.findByActive(status, Pageable.unpaged());
        if (products.isEmpty()) {
            throw new ProductNotFoundException();
        }
        return products.map(product -> modelMapper.map(product, ProductDto.class));
    }

    @Override
    public Page<ProductDto> getProductsByBrand(String brandName) throws BrandNotFoundException {
        String lowerCaseBrandName = brandName.toLowerCase();
        Page<Product> products = productRepository.findByBrandNameIgnoreCaseAndActiveTrue(lowerCaseBrandName, Pageable.unpaged());
        if (products.isEmpty()) {
            throw new BrandNotFoundException();
        }
        return products.map(product -> modelMapper.map(product, ProductDto.class));
    }

    @Override
    public Page<ProductDto> getProductsByCategory(String categoryName) throws CategoryNotFoundException {
        String lowerCaseCategoryName = categoryName.toLowerCase();
        Page<Product> products = productRepository.findByCategoryNameIgnoreCaseAndActiveTrue(lowerCaseCategoryName, Pageable.unpaged());
        if (products.isEmpty()) {
            throw new CategoryNotFoundException();
        }
        return products.map(product -> modelMapper.map(product, ProductDto.class));
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Set<Long> activeCategoryIds = categoryService.getAllActiveCategories()
                .stream()
                .map(CategoryDto::getId)
                .collect(Collectors.toSet());

        Set<Long> activeBrandIds = brandService.getAllActiveBrand()
                .stream()
                .map(BrandDto::getId)
                .collect(Collectors.toSet());

        validateCategoryAndBrand(productDto.getCategoryId(), productDto.getBrandId(), activeCategoryIds, activeBrandIds);

        Product product = modelMapper.map(productDto, Product.class);
        product.setActive(Boolean.TRUE);

        productRepository.save(product);
        return modelMapper.map(product, ProductDto.class);
    }

    private void validateCategoryAndBrand(Long categoryId, Long brandId, Set<Long> activeCategoryIds, Set<Long> activeBrandIds) {
        if (!activeCategoryIds.contains(categoryId)) {
            throw new CustomException("category-3", HttpStatus.BAD_REQUEST);
        }

        if (!activeBrandIds.contains(brandId)) {
            throw new CustomException("brand-3", HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    public void activateProduct(Long productId) {
        Product product = getProduct(productId);
        product.setActive(true);
        productRepository.save(product);
    }

    @Override
    public void deactivateProduct(Long productId) {
        Product product = getProduct(productId);
        product.setActive(false);
        productRepository.save(product);
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public void exportProductsToExcel(boolean active, HttpServletResponse response) throws IOException {
        List<Product> products = productRepository.findByActive(active, Pageable.unpaged()).getContent();
        excelExporter.exportProducts(products, response);
    }


    @Override
    public void exportProductsToPDF(boolean active, HttpServletResponse response) {
        List<Product> products = productRepository.findByActive(active, Pageable.unpaged()).getContent();
        try {
            pdfExporter.export(products, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
