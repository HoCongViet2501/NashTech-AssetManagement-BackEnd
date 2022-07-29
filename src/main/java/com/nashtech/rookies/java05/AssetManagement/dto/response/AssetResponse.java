package com.nashtech.rookies.java05.AssetManagement.dto.response;

import com.nashtech.rookies.java05.AssetManagement.model.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssetResponse {
    private String id;
    private String name;
    private Category category;
    private String specification;
    private Date installedDate;
    private String state;
}
