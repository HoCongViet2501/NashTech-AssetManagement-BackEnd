package com.nashtech.rookies.java05.AssetManagement.model.interfaces;

import java.util.Date;

public interface AssetHistoryInterface {
    Date getAssignedDate();
    String getRequestBy();
    String getAcceptedBy();
    Date getReturnedDate();
}
