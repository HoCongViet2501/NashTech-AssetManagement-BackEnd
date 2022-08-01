package com.nashtech.rookies.java05.AssetManagement.service.serviceImpl;

import com.nashtech.rookies.java05.AssetManagement.dto.request.CategoryRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.CategoryResponse;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceCategoryException;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Category;
import com.nashtech.rookies.java05.AssetManagement.repository.CategoryRepository;
import com.nashtech.rookies.java05.AssetManagement.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    private ModelMapper modelMapper;

    public CategoryServiceImpl() {
    }

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Optional<Category> categoryNameOptional = this.categoryRepository.findByName(categoryRequest.getName());
        Optional<Category> categoryPrefixOptional = this.categoryRepository.findById(categoryRequest.getId());

        if (categoryRequest.getName().isEmpty() &&  categoryRequest.getId().isEmpty()){
            throw new ResourceCategoryException("Category and Prefix are required");
        } else if (categoryRequest.getName().isEmpty()) {
            throw new ResourceCategoryException("Category is required");
        } else if (categoryRequest.getId().isEmpty()) {
            throw new ResourceCategoryException("Prefix is required");
        }

        if (categoryNameOptional.isPresent() &&  categoryPrefixOptional.isPresent()){
            throw new ResourceCategoryException("Category and Prefix are already existed");
        } else if (categoryNameOptional.isPresent()) {
            throw new ResourceCategoryException("Category is already existed. Please enter a different category");
        } else if (categoryPrefixOptional.isPresent()) {
            throw new ResourceCategoryException("Prefix is already existed. Please enter a different prefix");
        }

        Category category = modelMapper.map(categoryRequest, Category.class);
        Category saveCategory = categoryRepository.save(category);
        return modelMapper.map(saveCategory, CategoryResponse.class);
    }

    @Override
    public List<Category> getAllCategorys() {
        return this.categoryRepository.findAll();
    }
}
