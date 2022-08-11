package com.nashtech.rookies.java05.AssetManagement.service;

import com.nashtech.rookies.java05.AssetManagement.model.interfaces.ReportInterface;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public interface ReportService {
    List<ReportInterface> getReportListFinal();
    void exportReport(HttpServletResponse response) throws IOException;
}
