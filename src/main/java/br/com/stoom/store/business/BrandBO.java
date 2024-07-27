package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IBrandBO;
import br.com.stoom.store.dto.BrandDto;
import br.com.stoom.store.exception.CategoryNotFoundException;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.repository.BrandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandBO implements IBrandBO {
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<BrandDto> getAllActiveBrand() {
        List<Brand> categories = brandRepository.findByActiveTrue();

        if (categories.isEmpty()) {
            throw new CategoryNotFoundException();
        }
        return categories.stream()
                .map(category -> modelMapper.map(category, BrandDto.class))
                .toList();
    }

    @Override
    public void activateBrand(Long brandId) {
        Brand category = getBrand(brandId);
        category.setActive(true);
        brandRepository.save(category);
    }

    @Override
    public void deactivateBrand(Long brandId) {
        Brand category = getBrand(brandId);
        category.setActive(false);
        brandRepository.save(category);
    }

    private Brand getBrand(Long brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(CategoryNotFoundException::new);
    }
}
