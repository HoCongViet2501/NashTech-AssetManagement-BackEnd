package com.nashtech.rookies.java05.AssetManagement.controller;

import com.nashtech.rookies.java05.AssetManagement.dto.request.CategoryRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.CategoryResponse;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Category;
import com.nashtech.rookies.java05.AssetManagement.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR")
    })
    @Operation(summary = "get all category list")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<Category> getAllCategory(){
        return this.categoryService.getAllCategorys();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create Success"),
            @ApiResponse(responseCode = "417", description = "EXPECTATION_FAILED: Null or Already Existed"),
            @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR")
    })
    @Operation(summary = "create new category")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    CategoryResponse createCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        return this.categoryService.createCategory(categoryRequest);
    }
}
