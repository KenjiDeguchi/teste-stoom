package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.dto.BrandDto;
import br.com.stoom.store.dto.CategoryDto;

import java.util.List;

public interface IBrandBO {

    List<BrandDto> getAllActiveBrand();

    void activateBrand(Long categoryId);

    void deactivateBrand(Long categoryId);
}
