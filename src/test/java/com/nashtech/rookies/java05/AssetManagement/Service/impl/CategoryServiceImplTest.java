//package com.nashtech.rookies.java05.AssetManagement.Service.impl;
//
//import com.nashtech.rookies.java05.AssetManagement.dto.request.CategoryRequest;
//import com.nashtech.rookies.java05.AssetManagement.dto.response.CategoryResponse;
//import com.nashtech.rookies.java05.AssetManagement.exception.ResourceCategoryException;
//import com.nashtech.rookies.java05.AssetManagement.model.entity.Category;
//import com.nashtech.rookies.java05.AssetManagement.repository.CategoryRepository;
//import com.nashtech.rookies.java05.AssetManagement.service.serviceImpl.CategoryServiceImpl;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//
//import java.util.Optional;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class CategoryServiceImplTest {
//    CategoryServiceImpl categoryService;
//    CategoryRepository categoryRepository;
//    ModelMapper modelMapper;
//    Category initialCategory;
//    Category expectedCategory;
//    CategoryRequest categoryRequest;
//    CategoryResponse categoryResponse;
//
//
//    @BeforeEach
//    void BeforeEach(){
//        categoryRepository = mock(CategoryRepository.class);
//        modelMapper = mock(ModelMapper.class);
//        categoryService = new CategoryServiceImpl(categoryRepository, modelMapper);
//        initialCategory = mock(Category.class);
//        expectedCategory = mock(Category.class);
//        categoryRequest = mock(CategoryRequest.class);
//        categoryResponse = mock(CategoryResponse.class);
//    }
//
//    @Test
//    void createCategory_ShouldReturnErrorMessage_WhenDataNull(){
//        categoryRequest = new CategoryRequest("", "");
//        ResourceCategoryException resourceCategoryException = Assertions.assertThrows(ResourceCategoryException.class, () -> {
//            categoryService.createCategory(categoryRequest);
//        });
//        assertThat(resourceCategoryException.getMessage(), is("Category and Prefix are required"));
//    }
//
//    @Test
//    void createCategory_ShouldReturnErrorMessage_WhenCategoryNull(){
//        categoryRequest = new CategoryRequest("123", "");
//        ResourceCategoryException resourceCategoryException = Assertions.assertThrows(ResourceCategoryException.class, () -> {
//            categoryService.createCategory(categoryRequest);
//        });
//        assertThat(resourceCategoryException.getMessage(), is("Category is required"));
//    }
//
//    @Test
//    void createCategory_ShouldReturnErrorMessage_WhenPrefixNull(){
//        categoryRequest = new CategoryRequest("", "123");
//        ResourceCategoryException resourceCategoryException = Assertions.assertThrows(ResourceCategoryException.class, () -> {
//            categoryService.createCategory(categoryRequest);
//        });
//        assertThat(resourceCategoryException.getMessage(), is("Prefix is required"));
//    }
//
//    @Test
//    void createCategory_ShouldReturnObject_WhenDataValid(){
//        categoryRequest = new CategoryRequest("123", "123");
//        when(modelMapper.map(categoryRequest, Category.class)).thenReturn(initialCategory);
//        when(categoryRepository.save(initialCategory)).thenReturn(initialCategory);
//        when(modelMapper.map(initialCategory, CategoryResponse.class)).thenReturn(categoryResponse);
//        CategoryResponse result = categoryService.createCategory(categoryRequest);
//
//        assertThat(result, is(categoryResponse));
//    }
//}
