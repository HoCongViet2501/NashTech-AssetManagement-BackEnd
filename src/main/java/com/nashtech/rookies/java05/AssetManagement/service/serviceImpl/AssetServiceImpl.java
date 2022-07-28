package com.nashtech.rookies.java05.AssetManagement.service.serviceImpl;

import com.nashtech.rookies.java05.AssetManagement.dto.request.AssetRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssetResponse;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceCategoryException;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceNotFoundException;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Asset;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Category;
import com.nashtech.rookies.java05.AssetManagement.model.entity.User;
import com.nashtech.rookies.java05.AssetManagement.repository.AssetRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.CategoryRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.UserRepository;
import com.nashtech.rookies.java05.AssetManagement.service.AssetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
    public AssetResponse createAsset(AssetRequest assetRequest) {
        Asset asset = modelMapper.map(assetRequest, Asset.class);

        User creator = this.userRepository.findByUserName(getAuthentication().getName()).orElseThrow(() ->
                new ResourceNotFoundException("Not.Found.User.Id"));

        Category category = this.categoryRepository.findById(assetRequest.getCategory())
                .orElseThrow(() -> new ResourceCategoryException("Not.Found.Category.With.ID." + assetRequest.getCategory()));

        try {
            Asset lastAsset = assetRepository.findByOrderByIdDesc(assetRequest.getCategory()).get(0);
            String idGenerate = assetRequest.getCategory();

                int number = Integer.parseInt(lastAsset.getId().substring(assetRequest.getCategory().length()+1,lastAsset.getId().length()))+1;
                for (int i = String.valueOf(number).length() ; i < 6; i++) {
                    idGenerate += "0";
                }
                idGenerate += String.valueOf(number);
                asset.setId(idGenerate);

        }
        catch (Exception e){
            String idGenerate = assetRequest.getCategory();
            asset.setId(idGenerate+ "000001");
        }

        asset.setCategory(category);
        asset.setCreator(creator);

        Asset saveAsset = assetRepository.save(asset);
        return modelMapper.map(saveAsset, AssetResponse.class);
    }

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
