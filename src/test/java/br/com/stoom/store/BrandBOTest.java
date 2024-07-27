package br.com.stoom.store;

import br.com.stoom.store.business.BrandBO;
import br.com.stoom.store.dto.BrandDto;
import br.com.stoom.store.exception.CategoryNotFoundException;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.repository.BrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandBOTest {

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BrandBO brandBO;

    private Brand brand;
    private BrandDto brandDto;

    @BeforeEach
    void setUp() {
        brand = new Brand();
        brand.setId(1L);
        brand.setName("Brand1");
        brand.setActive(true);

        brandDto = new BrandDto();
        brandDto.setId(1L);
        brandDto.setName("Brand1");
        brandDto.setActive(true);
    }

    @Nested
    class Dado_uma_brand {

        @Nested
        class Quando_ativar {

            @Test
            void Entao_ative_brand() {
                brand.setActive(false);
                when(brandRepository.findById(anyLong())).thenReturn(Optional.of(brand));

                brandBO.activateBrand(1L);

                assertTrue(brand.isActive());
                verify(brandRepository, times(1)).save(brand);
            }
        }

        @Nested
        class Quando_desativar {

            @Test
            void Entao_desative_brand() {
                brand.setActive(true);
                when(brandRepository.findById(anyLong())).thenReturn(Optional.of(brand));

                brandBO.deactivateBrand(1L);

                assertFalse(brand.isActive());
                verify(brandRepository, times(1)).save(brand);
            }
        }

        @Nested
        class Quando_buscar_todas_ativas {

            @Test
            void Entao_retorne_lista_de_brands() {
                List<Brand> brands = new ArrayList<>();
                brands.add(brand);

                when(brandRepository.findByActiveTrue()).thenReturn(brands);
                when(modelMapper.map(any(Brand.class), eq(BrandDto.class))).thenReturn(brandDto);

                List<BrandDto> result = brandBO.getAllActiveBrand();

                assertNotNull(result);
                assertEquals(1, result.size());
                assertEquals(brandDto, result.get(0));
            }

            @Test
            void Entao_lance_excecao_quando_nao_houver_brands() {
                when(brandRepository.findByActiveTrue()).thenReturn(new ArrayList<>());

                assertThrows(CategoryNotFoundException.class, () -> brandBO.getAllActiveBrand());
            }
        }
    }
}
