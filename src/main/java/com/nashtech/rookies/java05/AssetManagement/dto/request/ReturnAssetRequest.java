package com.nashtech.rookies.java05.AssetManagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnAssetRequest {
    int assignmentId;
    String assignedBy;
    String assignedTo;
}
