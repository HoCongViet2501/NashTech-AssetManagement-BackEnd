package com.nashtech.rookies.java05.AssetManagement.dto.response;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.nashtech.rookies.java05.AssetManagement.model.entity.Returning;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
public class ReturningResponse {
    private Long id;
    private String assetCode;
    private String assetName;
    private Date returnDate;
    private String state;
    private Date assignedDate;
    private String requestBy;
    private String acceptedBy;
    
    /**
     * @param id
     * @param assetCode
     * @param assetName
     * @param returnDate
     * @param state
     * @param assignedDate
     * @param requestBy
     * @param acceptedBy
     */
    public ReturningResponse(Long id, String assetCode, String assetName, Date returnDate, String state,
                             Date assignedDate, String requestBy, String acceptedBy) {
        this.id = id;
        this.assetCode = assetCode;
        this.assetName = assetName;
        this.returnDate = returnDate;
        this.state = state;
        this.assignedDate = assignedDate;
        this.requestBy = requestBy;
        this.acceptedBy = acceptedBy;
    }
    
    public static ReturningResponse buildFromModel(Returning returning) {
        String assetCode = returning.getAssignment().getAsset().getId();
        String assetName = returning.getAssignment().getAsset().getName();
        String acceptedBy = returning.getAcceptedBy() == null ? "" : returning.getAcceptedBy().getUserName();
        
        return new ReturningResponse(returning.getId(), assetCode, assetName, returning.getReturnedDate(),
                returning.getState(), returning.getAssignment().getAssignedDate(),
                returning.getRequestBy().getUserName(), acceptedBy);
    }
    
}
