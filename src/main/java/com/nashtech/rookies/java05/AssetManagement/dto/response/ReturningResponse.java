package com.nashtech.rookies.java05.AssetManagement.dto.response;

import java.util.Date;

import com.nashtech.rookies.java05.AssetManagement.model.entity.Returning;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturningResponse {
    private long id;
    private String assetCode;
    private String assetName;
    private Date returnDate;
    private String status;
    private Date assignedDate;
    private String requestBy;
    private String acceptedBy;

    /**
     * @param id
     * @param assetCode
     * @param assetName
     * @param returnDate
     * @param status
     * @param assignedDate
     * @param requestBy
     * @param acceptedBy
     */
    public ReturningResponse(long id, String assetCode, String assetName, Date returnDate, String status,
            Date assignedDate, String requestBy, String acceptedBy) {
        this.id = id;
        this.assetCode = assetCode;
        this.assetName = assetName;
        this.returnDate = returnDate;
        this.status = status;
        this.assignedDate = assignedDate;
        this.requestBy = requestBy;
        this.acceptedBy = acceptedBy;
    }

    private static ReturningResponse build(Returning returning) {
        String assetCode = returning.getAssignment().getAsset().getId();
        String assetName = returning.getAssignment().getAsset().getName();

        return new ReturningResponse(returning.getId(), assetCode, assetName, returning.getReturnedDate(),
                returning.getState(), returning.getAssignment().getAssignedDate(), returning.getCreator().getUserName(),
                returning.getUser().getUserName());
    }

}
