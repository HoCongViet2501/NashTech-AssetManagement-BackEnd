package com.nashtech.rookies.java05.AssetManagement.model.interfaces;

public interface ReportInterface {
    String getName();
    Long getTotal();
    Long getAssigned();
    Long getAvailable();
    Long getNotAvailable();
    Long getWaitingForRecycling();
    Long getRecycled();
}
