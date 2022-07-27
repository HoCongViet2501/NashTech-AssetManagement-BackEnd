package com.nashtech.rookies.java05.AssetManagement.controller;

import com.nashtech.rookies.java05.AssetManagement.dto.request.CategoryRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.CategoryResponse;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Category;
import com.nashtech.rookies.java05.AssetManagement.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    List<Category> getAllCategory(){
        return this.categoryService.getAllCategorys();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CategoryResponse createCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        return this.categoryService.createCategory(categoryRequest);
    }
}
