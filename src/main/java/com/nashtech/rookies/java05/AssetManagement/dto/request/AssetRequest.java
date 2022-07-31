package com.nashtech.rookies.java05.AssetManagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssetRequest {
    private String name;
    private String category;
    private String specification;
    private Date installedDate;
    private String state;
}
