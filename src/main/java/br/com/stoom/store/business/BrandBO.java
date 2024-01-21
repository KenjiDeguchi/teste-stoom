package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IBrandBO;
import br.com.stoom.store.exceptions.BadRequestException;
import br.com.stoom.store.exceptions.NotFoundException;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.repository.BrandRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandBO implements IBrandBO {

    private final BrandRepository brandRepository;

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAllByActive(true);
    }

    public Brand findById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Brand not found"));
    }

    @Override
    public Brand save(Brand brand) {
        if (brandRepository.existsByName(brand.getName()))
            throw new BadRequestException("Brand name already registered");

        return brandRepository.save(brand);
    }

    @Override
    public void toggleBrand(Long id, boolean active) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Brand not found"));

        brand.setActive(active);

        brandRepository.save(brand);
    }

}
