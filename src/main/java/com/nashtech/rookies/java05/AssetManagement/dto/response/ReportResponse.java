package com.nashtech.rookies.java05.AssetManagement.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportResponse {
    String name;
    Long total = 0l;
    Long assigned = 0l;
    Long available= 0l;
    Long notAvailable= 0l;
    Long waitingForRecycling= 0l;
    Long recycled= 0l;
}
