package com.nashtech.rookies.java05.AssetManagement.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AllUserResponse {
    private int raw;
    private int totalRecord;
    private List<UserDetailResponse> users;
}
