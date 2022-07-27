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
        Optional<Category> categoryPrefixOptional = this.categoryRepository.findByPrefix(categoryRequest.getPrefix());

        if (categoryRequest.getName().isEmpty() &&  categoryRequest.getPrefix().isEmpty()){
            throw new ResourceCategoryException("Category.Name.and.Category.Prefix.Are.Required");
        } else if (categoryRequest.getName().isEmpty()) {
            throw new ResourceCategoryException("Category.Name.Is.Required");
        } else if (categoryRequest.getPrefix().isEmpty()) {
            throw new ResourceCategoryException("Category.Prefix.Is.Required");
        }

        if (categoryNameOptional.isPresent() &&  categoryPrefixOptional.isPresent()){
            throw new ResourceCategoryException("Category.Name.and.Category.Prefix.Are.Already.Existed");
        } else if (categoryNameOptional.isPresent()) {
            throw new ResourceCategoryException("Category.Name.Is.Already.Existed");
        } else if (categoryPrefixOptional.isPresent()) {
            throw new ResourceCategoryException("Category.Prefix.Is.Already.Existed");
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
