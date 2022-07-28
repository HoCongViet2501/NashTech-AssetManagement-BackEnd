package com.nashtech.rookies.java05.AssetManagement.service.serviceImpl;

import com.nashtech.rookies.java05.AssetManagement.dto.request.AssetRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssetResponse;
import com.nashtech.rookies.java05.AssetManagement.dto.response.CategoryResponse;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceCategoryException;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceNotFoundException;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Asset;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Category;
import com.nashtech.rookies.java05.AssetManagement.model.entity.User;
import com.nashtech.rookies.java05.AssetManagement.repository.AssetRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.CategoryRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.UserRepository;
import com.nashtech.rookies.java05.AssetManagement.service.AssetService;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class AssetServiceImpl implements AssetService {
    private AssetRepository assetRepository;
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public AssetServiceImpl() {
    }

    @Autowired
    public AssetServiceImpl(AssetRepository assetRepository, ModelMapper modelMapper, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.assetRepository = assetRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AssetResponse createAsset(AssetRequest assetRequest, String creatorId) {
        Asset asset = modelMapper.map(assetRequest, Asset.class);
        Asset lastAsset = assetRepository.findTop1OByOrderByIdDesc().get(0);

        User creator = this.userRepository.findById(creatorId).orElseThrow(() ->
                new ResourceNotFoundException("Not.Found.User.Id"));

        Category category = this.categoryRepository.findById(assetRequest.getCategory())
                .orElseThrow(() -> new ResourceCategoryException("Not.Found.Category.With.ID." + assetRequest.getCategory()));

        String idGenerate = assetRequest.getCategory();
        if (lastAsset == null) {
            asset.setId(idGenerate+ "000001");
        } else {
            int number = Integer.parseInt(lastAsset.getId().substring(assetRequest.getCategory().length()+1,lastAsset.getId().length()))+1;
            for (int i = String.valueOf(number).length() ; i < 6; i++) {
                idGenerate += "0";
            }
            idGenerate += String.valueOf(number);
            asset.setId(idGenerate);
        }

        asset.setCategory(category);
        asset.setCreator(creator);

        Asset saveAsset = assetRepository.save(asset);
        return modelMapper.map(saveAsset, AssetResponse.class);
    }
}
