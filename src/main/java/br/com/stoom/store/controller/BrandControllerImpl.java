package br.com.stoom.store.controller;

import br.com.stoom.store.business.interfaces.IBrandBO;
import br.com.stoom.store.controller.interfaces.IBrandController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrandControllerImpl implements IBrandController {

    @Autowired
    private IBrandBO brandService;

    @Override
    public ResponseEntity<Void> activateBrand(Long brandId) {
        brandService.activateBrand(brandId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> deactivateBrand(Long brandId) {
        brandService.deactivateBrand(brandId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
