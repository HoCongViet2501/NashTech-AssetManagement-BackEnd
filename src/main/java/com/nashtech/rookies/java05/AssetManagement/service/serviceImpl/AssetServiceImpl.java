package com.nashtech.rookies.java05.AssetManagement.service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nashtech.rookies.java05.AssetManagement.dto.request.AssetRequest;
import com.nashtech.rookies.java05.AssetManagement.dto.response.AssetResponse;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceCategoryException;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceCheckException;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceNotFoundException;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Asset;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Assignment;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Category;
import com.nashtech.rookies.java05.AssetManagement.model.entity.User;
import com.nashtech.rookies.java05.AssetManagement.repository.AssetRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.AssignmentRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.CategoryRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.UserRepository;
import com.nashtech.rookies.java05.AssetManagement.service.AssetService;

@Service
public class AssetServiceImpl implements AssetService {
    private AssetRepository assetRepository;
    private AssignmentRepository assignmentRepository;
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public AssetServiceImpl() {
    }

    @Autowired
    public AssetServiceImpl(AssetRepository assetRepository, AssignmentRepository assignmentRepository, CategoryRepository categoryRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.assetRepository = assetRepository;
        this.assignmentRepository = assignmentRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AssetResponse createAsset(AssetRequest assetRequest) {
        Asset asset = modelMapper.map(assetRequest, Asset.class);

        User creator = this.userRepository.findByUserName(getAuthentication().getName()).orElseThrow(() ->
                new ResourceCheckException("Not.Found.User.Id"));

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

    @Override
    public AssetResponse updateAsset(AssetRequest assetRequest, String id) {
        Asset asset = modelMapper.map(assetRequest, Asset.class);

        User user = this.userRepository.findByUserName(getAuthentication().getName()).orElseThrow(() ->
                new ResourceCheckException("Not.Found.User.Id"));

        Asset oldAsset = this.assetRepository.findById(id).orElseThrow(
                () -> new ResourceCheckException("Not.Found.Asset.Id")
        );

        asset.setId(oldAsset.getId());
        asset.setCategory(oldAsset.getCategory());
        asset.setCreator(user);
        Asset saveAsset = assetRepository.save(asset);
        return modelMapper.map(saveAsset, AssetResponse.class);
    }

    @Override
    public boolean deleteAsset(String id) {
        Asset asset = this.assetRepository.findById(id).orElseThrow(
                () -> new ResourceCheckException("Not.Found.Asset.Id")
        );

        try {
            List<Assignment> assignments = this.assignmentRepository.findAssignmentByAsset(asset.getId());
            
            if (assignments.isEmpty()){
                assetRepository.delete(asset);
                return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


  @Override
  public List<AssetResponse> getAllAssetByLocation(String location) {
    List<Asset> assets = assetRepository.findAssetByLocation(location);
    if (assets.isEmpty()) {
      throw new ResourceNotFoundException("No asset found in this location");
    }
    return assets.stream().map(AssetResponse::build).collect(Collectors.toList());
  }
  
  @Override
  public List<AssetResponse> searchAsset(String content, String location) {
    
    List<Asset> assets = assetRepository.searchAssetByNameOrCode(content, location);
    if (assets.isEmpty()) {
      throw new ResourceNotFoundException("No asset found in this location");
    }
    return assets.stream().map(AssetResponse::build).collect(Collectors.toList());
  }

    @Override
    public AssetResponse getAssetById(String id) {
        Asset asset = this.assetRepository.findById(id).orElseThrow(
                () -> new ResourceCheckException("Not.Found.Asset.With.ID." + id));
        return modelMapper.map(asset, AssetResponse.class);
    }
}
