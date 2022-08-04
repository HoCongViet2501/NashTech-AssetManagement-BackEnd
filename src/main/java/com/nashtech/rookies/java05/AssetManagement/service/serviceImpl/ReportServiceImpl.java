package com.nashtech.rookies.java05.AssetManagement.service.serviceImpl;

import com.nashtech.rookies.java05.AssetManagement.dto.response.ReportResponse;
import com.nashtech.rookies.java05.AssetManagement.generator.ReportExcelExport;
import com.nashtech.rookies.java05.AssetManagement.model.entity.Category;
import com.nashtech.rookies.java05.AssetManagement.model.interfaces.ReportInterface;
import com.nashtech.rookies.java05.AssetManagement.repository.CategoryRepository;
import com.nashtech.rookies.java05.AssetManagement.repository.ReportRepository;
import com.nashtech.rookies.java05.AssetManagement.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private ReportRepository reportRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, CategoryRepository categoryRepository) {
        this.reportRepository = reportRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ReportResponse> getReportListFinal() {
        List<Category> categoryList = this.categoryRepository.findAll();
        List<ReportInterface> reportInterfaceList = this.reportRepository.findAssetByCategoryAndState();
        List<ReportResponse> reportResponses = new ArrayList<>();

        for (Category category: categoryList) {
            ReportResponse reportResponse = new ReportResponse();
            for (int i = 0; i < reportInterfaceList.size(); i++) {
                if (category.getId().equals(reportInterfaceList.get(i).getId())) {
                    if ("Available".equals(reportInterfaceList.get(i).getState())){
                        reportResponse.setAvailable(reportInterfaceList.get(i).getCount());
                    }
                    if ("Not available".equals(reportInterfaceList.get(i).getState())){
                        reportResponse.setNotAvailable(reportInterfaceList.get(i).getCount());
                    }
                    if ("Waiting for recycling".equals(reportInterfaceList.get(i).getState())){
                        reportResponse.setWaitingForRecycling(reportInterfaceList.get(i).getCount());
                    }
                    if ("Recycled".equals(reportInterfaceList.get(i).getState())){
                        reportResponse.setRecycled(reportInterfaceList.get(i).getCount());
                    }
                    if ("Assigned".equals(reportInterfaceList.get(i).getState())){
                        reportResponse.setAssigned(reportInterfaceList.get(i).getCount());
                    }
                }
            }
            reportResponse.setName(category.getName());
            reportResponse.setTotal(reportResponse.getAssigned() + reportResponse.getRecycled() +reportResponse.getNotAvailable() + reportResponse.getWaitingForRecycling() + reportResponse.getAvailable());
            reportResponses.add(reportResponse);
        }
        return reportResponses;
    }

    @Override
    public void exportReport(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=report" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<ReportResponse> listReport = getReportListFinal();

        ReportExcelExport reportExcelExport = new ReportExcelExport(listReport);

        reportExcelExport.generateExcelFile(response);
    }
}
