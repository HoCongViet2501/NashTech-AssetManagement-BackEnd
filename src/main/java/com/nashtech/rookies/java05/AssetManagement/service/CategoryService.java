package com.nashtech.rookies.java05.AssetManagement.service;

import com.nashtech.rookies.java05.AssetManagement.dto.request.CategoryRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.CategoryResponse;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    List<Category> getAllCategorys();
}
