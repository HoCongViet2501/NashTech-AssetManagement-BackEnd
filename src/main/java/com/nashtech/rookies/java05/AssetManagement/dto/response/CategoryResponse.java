package com.nashtech.rookies.java05.AssetManagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;
    private String prefix;
}
