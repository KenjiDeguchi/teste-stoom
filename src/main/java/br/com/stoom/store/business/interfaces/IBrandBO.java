package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.model.Brand;
import java.util.List;

public interface IBrandBO {

    List<Brand> findAll();

    Brand findById(Long id);

    Brand save(Brand brand);

    void toggleBrand(Long id, boolean active);

}
