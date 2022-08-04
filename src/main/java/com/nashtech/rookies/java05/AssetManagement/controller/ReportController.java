package com.nashtech.rookies.java05.AssetManagement.controller;

import com.nashtech.rookies.java05.AssetManagement.dto.response.ReportResponse;
import com.nashtech.rookies.java05.AssetManagement.service.CategoryService;
import com.nashtech.rookies.java05.AssetManagement.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService reportService;
    private final CategoryService categoryService;

    @Autowired
    public ReportController(ReportService reportService, CategoryService categoryService) {
        this.reportService = reportService;
        this.categoryService = categoryService;
    }

    @GetMapping()
    public List<ReportResponse> getReportList() {
        return this.reportService.getReportListFinal();
    }

    @GetMapping("/export")
    public void exportIntoExcelFile(HttpServletResponse response) throws IOException {
        reportService.exportReport(response);
    }
}
