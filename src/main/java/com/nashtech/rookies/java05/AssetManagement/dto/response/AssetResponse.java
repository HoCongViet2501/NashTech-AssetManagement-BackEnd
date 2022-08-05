package com.nashtech.rookies.java05.AssetManagement.dto.response;

import java.util.Date;

import com.nashtech.rookies.java05.AssetManagement.model.entity.Asset;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	public static AssetResponse build(Asset asset) {
		return new AssetResponse(asset.getId(), asset.getName(), asset.getCategory(), asset.getSpecification(),
				asset.getInstalledDate(), asset.getState());
	}

}
