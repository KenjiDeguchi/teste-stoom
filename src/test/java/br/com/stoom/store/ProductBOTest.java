package br.com.stoom.store;

import br.com.stoom.store.business.ProductBO;
import br.com.stoom.store.business.interfaces.IBrandBO;
import br.com.stoom.store.business.interfaces.ICategoryBO;
import br.com.stoom.store.dto.BrandDto;
import br.com.stoom.store.dto.CategoryDto;
import br.com.stoom.store.dto.ProductDto;
import br.com.stoom.store.exception.BrandNotFoundException;
import br.com.stoom.store.exception.CategoryNotFoundException;
import br.com.stoom.store.exception.ProductNotFoundException;
import br.com.stoom.store.export.ProductExcelExporter;
import br.com.stoom.store.export.ProductPdfExporter;
import br.com.stoom.store.model.Product;
import br.com.stoom.store.repository.ProductRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductBOTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ProductExcelExporter excelExporter;

    @Mock
    private ProductPdfExporter pdfExporter;

    @Mock
    private ICategoryBO categoryService;

    @Mock
    private IBrandBO brandService;

    @InjectMocks
    private ProductBO productBO;

    private Product product;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Product1");
        product.setActive(true);

        productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("Product1");
        productDto.setActive(true);
    }

    @Nested
    class Dado_um_produto {

        @Nested
        class Quando_ativar {

            @Test
            void Entao_ative_produto() {
                product.setActive(false);
                when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

                productBO.activateProduct(1L);

                assertTrue(product.isActive());
                verify(productRepository, times(1)).save(product);
            }
        }

        @Nested
        class Quando_desativar {

            @Test
            void Entao_desative_produto() {
                product.setActive(true);
                when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

                productBO.deactivateProduct(1L);

                assertFalse(product.isActive());
                verify(productRepository, times(1)).save(product);
            }
        }

        @Nested
        class Quando_buscar_todos_ativos {

            @Test
            void Entao_retorne_lista_de_produtos() {
                List<Product> products = new ArrayList<>();
                products.add(product);
                Page<Product> productPage = new PageImpl<>(products);

                when(productRepository.findByActive(true, Pageable.unpaged())).thenReturn(productPage);
                when(modelMapper.map(any(Product.class), eq(ProductDto.class))).thenReturn(productDto);

                Page<ProductDto> result = productBO.getAllActiveProducts(true);

                assertNotNull(result);
                assertEquals(1, result.getTotalElements());
                assertEquals(productDto, result.getContent().get(0));
            }

            @Test
            void Entao_lance_excecao_quando_nao_houver_produtos() {
                when(productRepository.findByActive(true, Pageable.unpaged())).thenReturn(Page.empty());

                assertThrows(ProductNotFoundException.class, () -> productBO.getAllActiveProducts(true));
            }
        }

        @Nested
        class Quando_buscar_por_marca {

            @Test
            void Entao_retorne_lista_de_produtos() {
                List<Product> products = new ArrayList<>();
                products.add(product);
                Page<Product> productPage = new PageImpl<>(products);

                when(productRepository.findByBrandNameIgnoreCaseAndActiveTrue(anyString(), any(Pageable.class))).thenReturn(productPage);
                when(modelMapper.map(any(Product.class), eq(ProductDto.class))).thenReturn(productDto);

                Page<ProductDto> result = productBO.getProductsByBrand("brandName");

                assertNotNull(result);
                assertEquals(1, result.getTotalElements());
                assertEquals(productDto, result.getContent().get(0));
            }

            @Test
            void Entao_lance_excecao_quando_nao_houver_produtos() {
                when(productRepository.findByBrandNameIgnoreCaseAndActiveTrue(anyString(), any(Pageable.class))).thenReturn(Page.empty());

                assertThrows(BrandNotFoundException.class, () -> productBO.getProductsByBrand("brandName"));
            }
        }

        @Nested
        class Quando_buscar_por_categoria {

            @Test
            void Entao_retorne_lista_de_produtos() {
                List<Product> products = new ArrayList<>();
                products.add(product);
                Page<Product> productPage = new PageImpl<>(products);

                when(productRepository.findByCategoryNameIgnoreCaseAndActiveTrue(anyString(), any(Pageable.class))).thenReturn(productPage);
                when(modelMapper.map(any(Product.class), eq(ProductDto.class))).thenReturn(productDto);

                Page<ProductDto> result = productBO.getProductsByCategory("categoryName");

                assertNotNull(result);
                assertEquals(1, result.getTotalElements());
                assertEquals(productDto, result.getContent().get(0));
            }

            @Test
            void Entao_lance_excecao_quando_nao_houver_produtos() {
                when(productRepository.findByCategoryNameIgnoreCaseAndActiveTrue(anyString(), any(Pageable.class))).thenReturn(Page.empty());

                assertThrows(CategoryNotFoundException.class, () -> productBO.getProductsByCategory("categoryName"));
            }
        }


        @Nested
        class Quando_exportar_para_excel {

            @Test
            void Entao_exporte_para_excel() throws IOException {
                List<Product> products = new ArrayList<>();
                products.add(product);

                when(productRepository.findByActive(true, Pageable.unpaged())).thenReturn(new PageImpl<>(products));
                HttpServletResponse response = mock(HttpServletResponse.class);

                productBO.exportProductsToExcel(true, response);

                verify(excelExporter, times(1)).exportProducts(products, response);
            }
        }

        @Nested
        class Quando_exportar_para_pdf {

            @Test
            void Entao_exporte_para_pdf() throws IOException {
                List<Product> products = new ArrayList<>();
                products.add(product);

                when(productRepository.findByActive(true, Pageable.unpaged())).thenReturn(new PageImpl<>(products));
                HttpServletResponse response = mock(HttpServletResponse.class);

                productBO.exportProductsToPDF(true, response);

                verify(pdfExporter, times(1)).export(products, response);
            }
        }
    }
}


