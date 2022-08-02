//package com.nashtech.rookies.java05.AssetManagement.Service.impl;
//
//import com.nashtech.rookies.java05.AssetManagement.dto.request.AssetRequest;
//import com.nashtech.rookies.java05.AssetManagement.dto.response.AssetResponse;
//import com.nashtech.rookies.java05.AssetManagement.exception.ResourceCheckException;
//import com.nashtech.rookies.java05.AssetManagement.model.entity.Asset;
//import com.nashtech.rookies.java05.AssetManagement.model.entity.Category;
//import com.nashtech.rookies.java05.AssetManagement.model.entity.Role;
//import com.nashtech.rookies.java05.AssetManagement.model.entity.User;
//import com.nashtech.rookies.java05.AssetManagement.model.enums.UserRole;
//import com.nashtech.rookies.java05.AssetManagement.model.enums.UserStatus;
//import com.nashtech.rookies.java05.AssetManagement.repository.AssetRepository;
//import com.nashtech.rookies.java05.AssetManagement.repository.AssignmentRepository;
//import com.nashtech.rookies.java05.AssetManagement.repository.CategoryRepository;
//import com.nashtech.rookies.java05.AssetManagement.repository.UserRepository;
//import com.nashtech.rookies.java05.AssetManagement.service.serviceImpl.AssetServiceImpl;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.util.Optional;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.is;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class AssetServiceImplTest {
//    AssetServiceImpl assetService;
//    AssetRepository assetRepository;
//    AssignmentRepository assignmentRepository;
//    CategoryRepository categoryRepository;
//    UserRepository userRepository;
//    ModelMapper modelMapper;
//    Asset initialAsset;
//    Asset expectedAsset;
//    AssetRequest assetRequest;
//    AssetResponse assetResponse;
//    AssetResponse initialAssetResponse;
//    User initialUser;
//    User expectedUser;
//    Category initialCategory;
//    Category expectedCategory;
//
//    @BeforeEach
//    void BeforeEach(){
//        assetRepository = mock(AssetRepository.class);
//        assignmentRepository = mock(AssignmentRepository.class);
//        categoryRepository = mock(CategoryRepository.class);
//        userRepository = mock(UserRepository.class);
//        modelMapper = mock(ModelMapper.class);
//        assetService = new AssetServiceImpl(assetRepository, assignmentRepository, categoryRepository, userRepository, modelMapper);
//
//        initialAsset = mock(Asset.class);
//        expectedAsset = mock(Asset.class);
//        assetRequest = mock(AssetRequest.class);
//        assetResponse = mock(AssetResponse.class);
//        initialAssetResponse = mock(AssetResponse.class);
//        initialUser = mock(User.class);
//        expectedUser = mock(User.class);
//        initialCategory = mock(Category.class);
//        expectedCategory = mock(Category.class);
//    }
//
//    @Test
//    void createAsset_ShouldReturnObject_WhenDataValid(){
//        Date installedDate = new GregorianCalendar(2000,07,01).getTime();
//        String prefixCategory = "LP";
//        UserRole admin = UserRole.ADMIN;
//        UserStatus status = UserStatus.ACTIVE;
//        Role role = new Role(1L, admin, null);
//        initialUser = new User("SD0001", "a", "123", role, status, null, null, null,null, null, null);
//        assetRequest = new AssetRequest("Laptop", "LP", "Ram 8GB", installedDate,"Available");
//        Category category = new Category("LP", "Laptop", null);
//        initialAssetResponse = new AssetResponse("LP000001","Laptop", category, "Ram 8GB", installedDate,"Available");
//
//        when(modelMapper.map(assetRequest, Asset.class)).thenReturn(initialAsset);
//        when(userRepository.findByUserName("SD0001")).thenReturn(Optional.ofNullable(expectedUser));
//        when(categoryRepository.findById(prefixCategory)).thenReturn(Optional.ofNullable(expectedCategory));
//
//        initialAsset.setCategory(expectedCategory);
//        initialAsset.setCreator(expectedUser);
//
//        when(assetRepository.save(initialAsset)).thenReturn(expectedAsset);
//        when(modelMapper.map(expectedAsset, AssetResponse.class)).thenReturn(assetResponse);
//
//        //AssetResponse result = assetService.createAsset(assetRequest);
//        assertThat(initialAssetResponse, is(assetResponse));
//    }
//
//    @Test
//    void checkAssetHistory_ShouldReturnErrorMessage_WhenDataInValid(){
//        String id = "";
//        ResourceCheckException resourceCheckException = Assertions.assertThrows(ResourceCheckException.class, () -> {
//            assetRepository.findById(id);
//        });
//        assertThat(resourceCheckException.getMessage(), is("Not.Found.Asset.Id"));
//    }
//
//}
