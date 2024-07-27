package br.com.stoom.store.business;

import br.com.stoom.store.dto.CategoryDto;
import br.com.stoom.store.exception.CategoryNotFoundException;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.repository.CategoryRepository;
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
class CategoryBOTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CategoryBO categoryBO;

    private Category category;
    private CategoryDto categoryDto;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Category1");
        category.setActive(true);

        categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Category1");
        categoryDto.setActive(true);
    }

    @Nested
    class Dado_uma_categoria {

        @Nested
        class Quando_ativar {

            @Test
            void Entao_ative_categoria() {
                category.setActive(false);
                when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

                categoryBO.activateCategory(1L);

                assertTrue(category.isActive());
                verify(categoryRepository, times(1)).save(category);
            }
        }

        @Nested
        class Quando_desativar {

            @Test
            void Entao_desative_categoria() {
                category.setActive(true);
                when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

                categoryBO.deactivateCategory(1L);

                assertFalse(category.isActive());
                verify(categoryRepository, times(1)).save(category);
            }
        }

        @Nested
        class Quando_buscar_todas_ativas {

            @Test
            void Entao_retorne_lista_de_categorias() {
                List<Category> categories = new ArrayList<>();
                categories.add(category);

                when(categoryRepository.findByActiveTrue()).thenReturn(categories);
                when(modelMapper.map(any(Category.class), eq(CategoryDto.class))).thenReturn(categoryDto);

                List<CategoryDto> result = categoryBO.getAllActiveCategories();

                assertNotNull(result);
                assertEquals(1, result.size());
                assertEquals(categoryDto, result.get(0));
            }

            @Test
            void Entao_lance_excecao_quando_nao_houver_categorias() {
                when(categoryRepository.findByActiveTrue()).thenReturn(new ArrayList<>());

                assertThrows(CategoryNotFoundException.class, () -> categoryBO.getAllActiveCategories());
            }
        }
    }
}

