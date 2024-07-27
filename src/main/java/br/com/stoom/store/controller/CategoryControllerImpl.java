package br.com.stoom.store.controller;

import br.com.stoom.store.business.interfaces.ICategoryBO;
import br.com.stoom.store.controller.interfaces.ICategoryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryControllerImpl implements ICategoryController {

    @Autowired
    private ICategoryBO categoryService;

    @Override
    public ResponseEntity<Void> activateCategory(Long categoryId) {
        categoryService.activateCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> deactivateCategory(Long categoryId) {
        categoryService.deactivateCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
