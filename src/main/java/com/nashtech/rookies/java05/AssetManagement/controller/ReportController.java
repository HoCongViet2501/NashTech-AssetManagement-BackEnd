package com.nashtech.rookies.java05.AssetManagement.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.nashtech.rookies.java05.AssetManagement.model.interfaces.ReportInterface;
import com.nashtech.rookies.java05.AssetManagement.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping()
    public List<ReportInterface> getReportList() {
        return this.reportService.getReportListFinal();
    }

    @GetMapping("/export")
    public void exportIntoExcelFile(HttpServletResponse response) throws IOException {
        reportService.exportReport(response);
    }
}
